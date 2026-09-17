package com.yogasowbamart.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class CartDAO {

    private String dbUrl = "jdbc:h2:~/yogasowbamart;DB_CLOSE_DELAY=-1";
    private String dbUser = "sa";
    private String dbPass = "";

    // டேபிள் மற்றும் சாம்பிள் பொருட்களை ஆட்டோமேட்டிக்காக உருவாக்க இந்த கன்ஸ்ட்ரக்டர் உதவும்
    public CartDAO() {
        try {
            Class.forName("org.h2.Driver");
            try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
                 Statement stmt = conn.createStatement()) {
                
                // டேபிள்கள் இல்லை என்றால் உருவாக்கிக்கொள்ளும்
                stmt.execute("CREATE TABLE IF NOT EXISTS products (id INT PRIMARY KEY, name VARCHAR(255), price DOUBLE)");
                stmt.execute("CREATE TABLE IF NOT EXISTS cart_items (id INT AUTO_INCREMENT PRIMARY KEY, user_id INT, product_id INT, quantity INT)");
                
                // சாம்பிள் ப்ராடக்ட்டுகளைச் சேர்த்தல்
                stmt.execute("MERGE INTO products (id, name, price) KEY(id) VALUES (101, 'Sample Product A', 500)");
                stmt.execute("MERGE INTO products (id, name, price) KEY(id) VALUES (102, 'Sample Product B', 750)");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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