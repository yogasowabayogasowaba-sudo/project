package com.yogasowbamart.controller;

import com.yogasowbamart.dao.DBConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/sellerRegister")
public class SellerRegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Connection conn = DBConnection.getConnection();
            
           
            PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM sellers WHERE email = ?");
            checkStmt.setString(1, email);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                out.println("<script>alert('Email already registered!'); window.location='sellerRegister.html';</script>");
                return;
            }

           
            String sql = "INSERT INTO sellers (name, email, password) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, password);

            int i = pstmt.executeUpdate();

            if (i > 0) {
                out.println("<script>alert('Registration successful! Please login.'); window.location='sellerLogin.html';</script>");
            } else {
                out.println("<script>alert('Registration Failed!'); window.location='sellerRegister.html';</script>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3 style='color:red; text-align:center;'>Error: " + e.getMessage() + "</h3>");
        }
    }
}