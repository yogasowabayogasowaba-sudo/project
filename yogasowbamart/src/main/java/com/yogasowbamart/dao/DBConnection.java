

      package com.yogasowbamart.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    
    private static final String URL = "jdbc:mysql://localhost:3306/yogasowbamart?useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASSWORD = "yoga@2007"; // பாஸ்வேர்ட் காலியாக விடப்பட்டுள்ளது

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}