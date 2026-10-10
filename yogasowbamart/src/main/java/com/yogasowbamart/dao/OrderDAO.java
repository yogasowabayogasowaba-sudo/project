package com.yogasowbamart.dao;

import com.yogasowbamart.model.Order;
import com.yogasowbamart.dao.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    
    public boolean placeOrder(String userEmail, String[] selectedItems) {
        if (selectedItems == null || selectedItems.length == 0) {
            return false;
        }

        boolean isSuccess = false;
        
        String getUserNameQuery = "SELECT name FROM users WHERE email = ?";
      
        String getCartItemQuery = "SELECT product_id, product_name, price, quantity FROM cart WHERE user_email = ? AND (product_name = ? OR product_name LIKE ?)";
      
        String insertQuery = "INSERT INTO orders (user_email, buyer_name, product_id, product_name, price, quantity, total_amount, order_date, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String deleteCartQuery = "DELETE FROM cart WHERE user_email = ?"; 

        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

    
            String buyerName = "Guest";
            try (PreparedStatement pstmtUser = conn.prepareStatement(getUserNameQuery)) {
                pstmtUser.setString(1, userEmail);
                try (ResultSet rsUser = pstmtUser.executeQuery()) {
                    if (rsUser.next()) {
                        String name = rsUser.getString("name");
                        if (name != null && !name.trim().isEmpty()) {
                            buyerName = name;
                        }
                    }
                }
            } catch (SQLException e) {
            
                e.printStackTrace();
            }



            try (PreparedStatement pstmtGetCart = conn.prepareStatement(getCartItemQuery);
                 PreparedStatement pstmtInsert = conn.prepareStatement(insertQuery);
                 PreparedStatement pstmtDelete = conn.prepareStatement(deleteCartQuery)) {

                for (String item : selectedItems) {
                    int productId = 0; // புதிய productId மாறி
                    double price = 0.0;
                    int quantity = 1;
                    String dbProductName = item;

                    String actualProductName = item;
                    if (item.contains("_")) {
                        int lastIndex = item.lastIndexOf("_");
                        actualProductName = item.substring(0, lastIndex);
                    }

                    String keyword = "%" + actualProductName + "%";
                    if (actualProductName.toLowerCase().contains("kurti")) {
                        keyword = "%Kurti%";
                    } else if (actualProductName.toLowerCase().contains("jeans")) {
                        keyword = "%Jeans%";
                    }

                    pstmtGetCart.setString(1, userEmail);
                    pstmtGetCart.setString(2, actualProductName);
                    pstmtGetCart.setString(3, keyword);
                    
                    try (ResultSet rs = pstmtGetCart.executeQuery()) {
                        if (rs.next()) {
                            productId = rs.getInt("product_id"); // கார்ட்டில் இருந்து product_id எடுக்கப்படுகிறது
                            dbProductName = rs.getString("product_name");
                            price = rs.getDouble("price");
                            quantity = rs.getInt("quantity");
                        }
                    }

                    if (price == 0.0) {
                        String lowerItem = actualProductName.toLowerCase();
                        if (lowerItem.contains("kurti")) {
                            price = 599.0;
                        } else if (lowerItem.contains("jeans")) {
                            price = 999.0;
                        } else if (lowerItem.contains("yoga mat")) {
                            price = 799.0;
                        }
                    }
                    double totalAmount = price * quantity;

                    pstmtInsert.setString(1, userEmail);
                    pstmtInsert.setString(2, buyerName); 
                    pstmtInsert.setInt(3, productId); // 3-வது இடத்தில் product_id செட் செய்யப்படுகிறது
                    pstmtInsert.setString(4, dbProductName); 
                    pstmtInsert.setDouble(5, price);
                    pstmtInsert.setInt(6, quantity);
                    pstmtInsert.setDouble(7, totalAmount);
                    pstmtInsert.setString(8, currentDate);
                    pstmtInsert.setString(9, "Placed");
                    pstmtInsert.addBatch();
                }
                
                pstmtInsert.executeBatch();

                pstmtDelete.setString(1, userEmail);
                pstmtDelete.executeUpdate();

                conn.commit();
                isSuccess = true;

            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                isSuccess = false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isSuccess;
    }


    public List<Order> getAllOrders() {
        List<Order> ordersList = new ArrayList<>();
        
        String query = "SELECT order_id, product_id, user_email, buyer_name, product_name, price, quantity, total_amount, order_date, status FROM orders ORDER BY order_id DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setProductId(rs.getInt("product_id")); // product_id மேப் செய்யப்படுகிறது
                order.setCustomerEmail(rs.getString("user_email"));
                order.setBuyerName(rs.getString("buyer_name")); 
                order.setProductName(rs.getString("product_name"));
                order.setPrice(rs.getDouble("price"));
                order.setQuantity(rs.getInt("quantity"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setOrderDate(rs.getString("order_date"));
                order.setStatus(rs.getString("status"));
                
                ordersList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordersList;
    }

    
    public List<Order> getOrdersByUser(String userEmail) {
        List<Order> ordersList = new ArrayList<>();
        
        String safeQuery = "SELECT order_id, product_id, user_email, buyer_name, product_name, price, quantity, total_amount, order_date, status FROM orders WHERE user_email = ? ORDER BY order_id DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(safeQuery)) {
            
            pstmt.setString(1, userEmail);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setOrderId(rs.getInt("order_id"));
                    order.setProductId(rs.getInt("product_id")); // product_id இங்கு செட் செய்யப்படுகிறது
                    order.setCustomerEmail(rs.getString("user_email"));
                    order.setBuyerName(rs.getString("buyer_name"));
                    order.setProductName(rs.getString("product_name"));
                    order.setPrice(rs.getDouble("price"));
                    order.setQuantity(rs.getInt("quantity"));
                    order.setTotalAmount(rs.getDouble("total_amount"));
                    order.setOrderDate(rs.getString("order_date"));
                    order.setStatus(rs.getString("status"));
                    
                    ordersList.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordersList;
    }
}