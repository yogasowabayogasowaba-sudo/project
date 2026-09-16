package com.yogasowbamart.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class CartDAO {

    private String dbUrl = "jdbc:h2:~/yogasowbamart;DB_CLOSE_DELAY=-1";
    private String dbUser = "sa";
    private String dbPass = "";

    public boolean addToCart(int userId, int productId, int quantity) {
        String query = "INSERT INTO cart_items (user_id, product_id, quantity) VALUES (?, ?, ?)";
        
        try {
            Class.forName("org.h2.Driver");
            try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
                 PreparedStatement ps = conn.prepareStatement(query)) {
                
                ps.setInt(1, userId);
                ps.setInt(2, productId);
                ps.setInt(3, quantity);

                int rows = ps.executeUpdate();
                return rows > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}