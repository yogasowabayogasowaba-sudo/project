package com.yogasowbamart.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDAO {

    private final String DB_URL = "jdbc:mysql://localhost:3306/yogasowbamart?useSSL=false&serverTimezone=UTC";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "yoga@2007";

    
    public boolean addProduct(String name, String description, double price, int stock, String category) {
        boolean success = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "INSERT INTO products (name, description, price, stock, category) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setDouble(3, price);
            stmt.setInt(4, stock);
            stmt.setString(5, category);
            
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                success = true;
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("DAO Add Error: " + e.getMessage());
        }
        return success;
    }

    // 2. அனைத்து தயாரிப்புகளையும் பெறுதல் (Get All Products)
    public List<Map<String, String>> getAllProducts() {
        List<Map<String, String>> productList = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "SELECT * FROM products";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, String> product = new HashMap<>();
                product.put("id", String.valueOf(rs.getInt("id")));
                product.put("name", rs.getString("name"));
                product.put("description", rs.getString("description"));
                product.put("price", String.valueOf(rs.getDouble("price")));
                product.put("stock", String.valueOf(rs.getInt("stock")));
                product.put("category", rs.getString("category"));
                productList.add(product);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("DAO GetAll Error: " + e.getMessage());
        }
        return productList;
    }

    
    public Map<String, String> getProductById(int id) {
        Map<String, String> product = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "SELECT * FROM products WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                product = new HashMap<>();
                product.put("id", String.valueOf(rs.getInt("id")));
                product.put("name", rs.getString("name"));
                product.put("description", rs.getString("description"));
                product.put("price", String.valueOf(rs.getDouble("price")));
                product.put("stock", String.valueOf(rs.getInt("stock")));
                product.put("category", rs.getString("category"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("DAO GetById Error: " + e.getMessage());
        }
        return product;
    }

    
    public boolean updateProduct(int id, String name, String description, double price, int stock, String category) {
        boolean success = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "UPDATE products SET name=?, description=?, price=?, stock=?, category=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setDouble(3, price);
            stmt.setInt(4, stock);
            stmt.setString(5, category);
            stmt.setInt(6, id);
            
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                success = true;
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("DAO Update Error: " + e.getMessage());
        }
        return success;
    }

    
    public boolean deleteProduct(int id) {
        boolean success = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "DELETE FROM products WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                success = true;
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("DAO Delete Error: " + e.getMessage());
        }
        return success;
    }

   
    public List<Map<String, String>> searchProducts(String keyword, String category) {
        List<Map<String, String>> productList = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "SELECT * FROM products WHERE name LIKE ? OR category LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + category + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, String> product = new HashMap<>();
                product.put("id", String.valueOf(rs.getInt("id")));
                product.put("name", rs.getString("name"));
                product.put("description", rs.getString("description"));
                product.put("price", String.valueOf(rs.getDouble("price")));
                product.put("stock", String.valueOf(rs.getInt("stock")));
                product.put("category", rs.getString("category"));
                productList.add(product);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("DAO Search Error: " + e.getMessage());
        }
        return productList;
    }
}