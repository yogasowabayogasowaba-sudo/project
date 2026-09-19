package com.yogasowbamart.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    private String dbUrl = "jdbc:h2:~/yogasowbamart;DB_CLOSE_DELAY=-1";
    private String dbUser = "sa";
    private String dbPass = "";

    public CartDAO() {
        try {
            Class.forName("org.h2.Driver");
            try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
                 Statement stmt = conn.createStatement()) {
                
                // டேபிள்கள் இல்லை என்றால் மட்டும் உருவாக்கிக்கொள்ளும்
                stmt.execute("CREATE TABLE IF NOT EXISTS products (id INT PRIMARY KEY, name VARCHAR(255), price DOUBLE)");
                stmt.execute("CREATE TABLE IF NOT EXISTS cart_items (id INT AUTO_INCREMENT PRIMARY KEY, email VARCHAR(255), product_id INT, quantity INT)");
                
                // சாம்பிள் ப்ராடக்ட்டுகளைச் சேர்த்தல் (இது எப்போதும் டேபிளில் இருக்கும்)
                stmt.execute("MERGE INTO products (id, name, price) KEY(id) VALUES (101, 'Sample Product A', 500)");
                stmt.execute("MERGE INTO products (id, name, price) KEY(id) VALUES (102, 'Sample Product B', 750)");
            }
        } catch (Exception e)  {
            e.printStackTrace();
        }
    }

    public boolean addToCart(String email, int productId, int quantity) {
        String query = "INSERT INTO cart_items (email, product_id, quantity) VALUES (?, ?, ?)";
        
        try {
            Class.forName("org.h2.Driver");
            try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
                 PreparedStatement ps = conn.prepareStatement(query)) {
                
                ps.setString(1, email);
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

    public List<String[]> getCartItems(String email) {
        List<String[]> items = new ArrayList<>();
        String query = "SELECT p.name, p.price, c.quantity FROM cart_items c JOIN products p ON c.product_id = p.id WHERE c.email = ?";
        
        try {
            Class.forName("org.h2.Driver");
            try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
                 PreparedStatement ps = conn.prepareStatement(query)) {
                
                ps.setString(1, email);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String name = rs.getString("name");
                        String price = String.valueOf(rs.getDouble("price"));
                        String qty = String.valueOf(rs.getInt("quantity"));
                        items.add(new String[] { name, price, qty });
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }
}