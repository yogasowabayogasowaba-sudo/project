package com.yogasowbamart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    public CartDAO() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            
            String createTableQuery = "CREATE TABLE IF NOT EXISTS cart (" +
                    "cart_id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "user_email VARCHAR(255) NOT NULL, " +
                    "product_name VARCHAR(255) NOT NULL, " +
                    "price DOUBLE NOT NULL, " +
                    "quantity INT NOT NULL)";
            stmt.execute(createTableQuery);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // கார்ட்டில் பொருளைச் சேர்க்க
    public boolean addToCart(String userEmail, String productName, double price, int quantity) {
        String query = "INSERT INTO cart (user_email, product_name, price, quantity) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, userEmail);
            ps.setString(2, productName);
            ps.setDouble(3, price);
            ps.setInt(4, quantity);
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // பயனரின் கார்ட்டில் உள்ள பொருட்களைப் பெற
    public List<String[]> getCartItems(String userEmail) {
        List<String[]> cartItems = new ArrayList<>();
        String query = "SELECT product_name, price, quantity FROM cart WHERE user_email = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, userEmail);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String[] item = new String[3];
                    item[0] = rs.getString("product_name");
                    item[1] = String.valueOf(rs.getDouble("price"));
                    item[2] = String.valueOf(rs.getInt("quantity"));
                    cartItems.add(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    // கார்ட்டில் இருந்து ஒரு குறிப்பிட்ட பொருளை மட்டும் நீக்க
    public boolean removeFromCart(String userEmail, String productName) {
        String query = "DELETE FROM cart WHERE user_email = ? AND product_name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, userEmail);
            ps.setString(2, productName);
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // முழு கார்ட்டைக் காலி செய்ய
    public void clearCart(String userEmail) {
        String query = "DELETE FROM cart WHERE user_email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, userEmail);
            ps.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ஆர்டர் செய்த குறிப்பிட்ட பொருட்களை மட்டும் கார்ட்டில் இருந்து நீக்க
    public void removeSelectedItems(String userEmail, List<String> productNames) {
        if (productNames == null || productNames.isEmpty()) {
            return;
        }
        
        String query = "DELETE FROM cart WHERE user_email = ? AND product_name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            for (String productName : productNames) {
                ps.setString(1, userEmail);
                ps.setString(2, productName);
                ps.addBatch();
            }
            ps.executeBatch();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}