package com.yogasowbamart.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BuyerDAO {
    

    public boolean registerBuyer(String name, String email, String password) {
        boolean isSuccess = false;
        try {
            Connection con = DBConnection.getConnection();
            String query = "INSERT INTO buyers (name, email, password) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            int i = ps.executeUpdate();
            if (i > 0) {
                isSuccess = true;
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSuccess;
    }

   
    public boolean validateBuyer(String email, String password) {
        boolean isValid = false;
        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT * FROM buyers WHERE email = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                isValid = true;
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValid;
    }
}