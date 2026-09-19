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
            
            // டேபிள் இல்லாவிட்டால் மட்டும் உருவாக்குகிறது (பழைய டேபிள் அழியாது)
            String createTableQuery = "CREATE TABLE IF NOT EXISTS cart_items (" +
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

    public boolean addToCart(String userEmail, String productName, double price, int quantity) {
        String query = "INSERT INTO cart_items (user_email, product_name, price, quantity) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, userEmail);
            ps.setString(2, productName);
            ps.setDouble(3, price);
            ps.setInt(4, quantity);
            
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<String[]> getCartItems(String userEmail) {
        List<String[]> cartList = new ArrayList<>();
        String query = "SELECT product_name, price, quantity FROM cart_items WHERE user_email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, userEmail);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String[] item = {
                        rs.getString("product_name"),
                        String.valueOf(rs.getDouble("price")),
                        String.valueOf(rs.getInt("quantity"))
                    };
                    cartList.add(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cartList;
    }

    public boolean clearCart(String userEmail) {
        String query = "DELETE FROM cart_items WHERE user_email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, userEmail);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}