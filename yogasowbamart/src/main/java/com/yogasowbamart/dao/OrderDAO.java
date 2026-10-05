package com.yogasowbamart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.yogasowbamart.model.Order;

public class OrderDAO {
    public OrderDAO() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            
            String createTableQuery = "CREATE TABLE IF NOT EXISTS orders (" +
                    "order_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "user_email VARCHAR(255) NOT NULL, " +
                    "product_name VARCHAR(255) NOT NULL, " +
                    "price DOUBLE NOT NULL, " +
                    "quantity INT NOT NULL, " +
                    "total_amount DOUBLE NOT NULL, " +
                    "status VARCHAR(50) DEFAULT 'Confirmed', " +
                    "order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
            stmt.execute(createTableQuery);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean placeOrder(String userEmail, String[] selectedItems) {
        if (selectedItems == null || selectedItems.length == 0) {
            return false;
        }

        CartDAO cartDAO = new CartDAO();
        List<String[]> allCartItems = cartDAO.getCartItems(userEmail);
        
        if (allCartItems.isEmpty()) {
            return false;
        }

        String insertQuery = "INSERT INTO orders (user_email, product_name, price, quantity, total_amount, status) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(insertQuery)) {
            
            boolean itemsAdded = false;
            List<String> orderedProductNames = new ArrayList<>();

            for (String val : selectedItems) {
                if (val == null || val.trim().isEmpty()) continue;

                for (int i = 0; i < allCartItems.size(); i++) {
                    String[] item = allCartItems.get(i);
                    boolean isMatch = false;

                    // 1. நேராக பொருளின் பெயரே வந்திருந்தால்
                    if (item[0].equals(val)) {
                        isMatch = true;
                    } 
                    // 2. இன்டெக்ஸ் அல்லது format ஆக வந்திருந்தால்
                    else {
                        try {
                            if (Integer.parseInt(val) == i) {
                                isMatch = true;
                            }
                        } catch (NumberFormatException e) {
                            String[] parts = val.split("_");
                            if (parts.length > 0) {
                                try {
                                    int idx = Integer.parseInt(parts[parts.length - 1]);
                                    if (idx == i) isMatch = true;
                                } catch (Exception ex) {}
                            }
                        }
                    }

                    if (isMatch) {
                        double price = Double.parseDouble(item[1]);
                        int quantity = Integer.parseInt(item[2]);
                        double totalAmount = price * quantity;

                        ps.setString(1, userEmail);
                        ps.setString(2, item[0]); // product_name
                        ps.setDouble(3, price);
                        ps.setInt(4, quantity);
                        ps.setDouble(5, totalAmount);
                        ps.setString(6, "Confirmed");
                        ps.addBatch();
                        
                        orderedProductNames.add(item[0]);
                        itemsAdded = true;
                        break;
                    }
                }
            }
            
            if (!itemsAdded) {
                return false;
            }

            ps.executeBatch();
            
            // ஆர்டர் செய்த குறிப்பிட்ட பொருட்களை மட்டும் கார்ட்டில் இருந்து நீக்குகிறோம்
            cartDAO.removeSelectedItems(userEmail, orderedProductNames);
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Order> getOrdersByUser(String userEmail) {
        List<Order> orderList = new ArrayList<>();
        String query = "SELECT order_id, product_name, price, quantity, total_amount, status, order_date FROM orders WHERE user_email = ? ORDER BY order_date DESC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, userEmail);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setOrderId(rs.getInt("order_id"));
                    order.setProductName(rs.getString("product_name"));
                    order.setPrice(rs.getDouble("price"));
                    order.setQuantity(rs.getInt("quantity"));
                    order.setTotalAmount(rs.getDouble("total_amount"));
                    order.setStatus(rs.getString("status"));
                    order.setOrderDate(rs.getString("order_date"));
                    orderList.add(order);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderList;
    }
}