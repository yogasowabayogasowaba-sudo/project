package com.yogasowbamart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.yogasowbamart.model.Order;

public class OrderDAO {

    // கன்ஸ்ட்ரக்டர்: டேபிள் இல்லையென்றால் மட்டும் புதியதாக உருவாக்குகிறது (பழைய டேபிளை டெலிட் செய்யாது)
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

    // கார்ட்டில் உள்ள பொருட்களை ஆர்டராக மாற்றி டேட்டாபேஸில் சேமிக்கும் மெத்தட்
    public boolean placeOrder(String userEmail) {
        CartDAO cartDAO = new CartDAO();
        List<String[]> cartItems = cartDAO.getCartItems(userEmail);
        
        if (cartItems.isEmpty()) {
            return false; // கார்ட் காலியாக இருந்தால் ஆர்டர் செய்ய முடியாது
        }

        String insertQuery = "INSERT INTO orders (user_email, product_name, price, quantity, total_amount, status) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(insertQuery)) {
            
            for (String[] item : cartItems) {
                double price = Double.parseDouble(item[1]);
                int quantity = Integer.parseInt(item[2]);
                double totalAmount = price * quantity;

                ps.setString(1, userEmail);
                ps.setString(2, item[0]); // product_name
                ps.setDouble(3, price); // price
                ps.setInt(4, quantity); // quantity
                ps.setDouble(5, totalAmount); // total_amount
                ps.setString(6, "Confirmed"); // status
                ps.addBatch();
            }
            
            ps.executeBatch(); // அனைத்து பொருட்களையும் ஒட்டுமொத்தமாக சேமித்தல்
            
            // ஆர்டர் வெற்றிகரமாக முடிந்ததும் கார்ட்டை காலியாக்குதல்
            cartDAO.clearCart(userEmail);
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // குறிப்பிட்ட பயனரின் ஆர்டர் வரலாற்றைப் பெறும் மெத்தட்
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