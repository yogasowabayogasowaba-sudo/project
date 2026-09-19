package com.yogasowbamart.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // H2 Driver பதிவு செய்தல்
            Class.forName("org.h2.Driver");
            // H2 டேட்டாபேஸ் URL (உங்கள் ப்ராஜெக்ட் தேவையின்படி)
            conn = DriverManager.getConnection("jdbc:h2:~/yogasowbamart", "sa", "");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}