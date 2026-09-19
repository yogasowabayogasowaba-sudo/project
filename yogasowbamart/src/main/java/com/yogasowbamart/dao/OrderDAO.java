package com.yogasowbamart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    // Constructor-லேயே டேபிள் ஆட்டோமேட்டிக்காக கிரியேட் ஆகிவிடும்
    public OrderDAO() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS orders (" +
                "order_id INT AUTO_INCREMENT PRIMARY KEY, " +
                "user_email VARCHAR(255) NOT NULL, " +
                "product_name VARCHAR(255) NOT NULL, " +
                "quantity INT NOT NULL, " +
                "total_amount DOUBLE NOT NULL, " +
                "order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "status VARCHAR(50) DEFAULT 'PENDING')";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // டேட்டாபேஸில் ஆர்டரைச் சேமிக்க
    public boolean saveOrder(String userEmail, String productName, int quantity, double totalAmount) {
        String query = "INSERT INTO orders (user_email, product_name, quantity, total_amount, order_date, status) VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, 'PENDING')";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, userEmail);
            pstmt.setString(2, productName);
            pstmt.setInt(3, quantity);
            pstmt.setDouble(4, totalAmount);
            
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // குறிப்பிட்ட வாடிக்கையாளரின் (Buyer) ஆர்டர் வரலாற பெற
    public List<Order> getOrdersByUser(String userEmail) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE user_email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, userEmail);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setOrderId(rs.getInt("order_id"));
                    order.setUserEmail(rs.getString("user_email"));
                    order.setProductName(rs.getString("product_name"));
                    order.setQuantity(rs.getInt("quantity"));
                    order.setTotalAmount(rs.getDouble("total_amount"));
                    order.setOrderDate(rs.getTimestamp("order_date"));
                    order.setStatus(rs.getString("status"));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    // அனைத்து ஆர்டர்களையும் (Seller பார்ப்பதற்கு) பெற
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserEmail(rs.getString("user_email"));
                order.setProductName(rs.getString("product_name"));
                order.setQuantity(rs.getInt("quantity"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setStatus(rs.getString("status"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}