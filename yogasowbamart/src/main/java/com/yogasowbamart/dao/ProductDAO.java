package com.yogasowbamart.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private String dbUrl = "jdbc:h2:~/yogasowbamart;DB_CLOSE_DELAY=-1";
    private String dbUser = "sa";
    private String dbPass = "";

    public ProductDAO() {
        try {
            Class.forName("org.h2.Driver");
            try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
                 Statement stmt = conn.createStatement()) {
                
                // பழைய டேபிளை நீக்கிவிட்டு புதிய கால்களுடன் டேபிளை உருவாக்குதல்
                stmt.execute("DROP TABLE IF EXISTS products");
                
                stmt.execute("CREATE TABLE products (" +
                        "id INT PRIMARY KEY, " +
                        "name VARCHAR(255), " +
                        "description VARCHAR(500), " +
                        "price DOUBLE, " +
                        "category VARCHAR(100))");

                // சாம்பிள் ப்ராடக்ட்டுகள்
                stmt.execute("MERGE INTO products (id, name, description, price, category) KEY(id) VALUES (101, 'Laptop', 'High performance laptop', 45000, 'Electronics')");
                stmt.execute("MERGE INTO products (id, name, description, price, category) KEY(id) VALUES (102, 'Running Shoes', 'Comfortable sports shoes', 1500, 'Footwear')");
                stmt.execute("MERGE INTO products (id, name, description, price, category) KEY(id) VALUES (103, 'Smartphone', 'Latest 5G smartphone', 18000, 'Electronics')");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String[]> searchProducts(String keyword, String category) {
        List<String[]> productList = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM products WHERE 1=1");

        if (keyword != null && !keyword.trim().isEmpty()) {
            query.append(" AND (LOWER(name) LIKE ? OR LOWER(description) LIKE ?)");
        }
        if (category != null && !category.trim().isEmpty() && !category.equals("All")) {
            query.append(" AND category = ?");
        }

        try {
            Class.forName("org.h2.Driver");
            try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
                 PreparedStatement ps = conn.prepareStatement(query.toString())) {
                
                int paramIndex = 1;
                if (keyword != null && !keyword.trim().isEmpty()) {
                    String searchPattern = "%" + keyword.toLowerCase().trim() + "%";
                    ps.setString(paramIndex++, searchPattern);
                    ps.setString(paramIndex++, searchPattern);
                }
                if (category != null && !category.trim().isEmpty() && !category.equals("All")) {
                    ps.setString(paramIndex++, category);
                }

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String[] prod = {
                            String.valueOf(rs.getInt("id")),
                            rs.getString("name"),
                            rs.getString("description"),
                            String.valueOf(rs.getDouble("price")),
                            rs.getString("category")
                        };
                        productList.add(prod);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productList;
    }
}