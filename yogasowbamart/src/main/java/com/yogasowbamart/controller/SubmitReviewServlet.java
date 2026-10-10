package com.yogasowbamart.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.yogasowbamart.dao.DBConnection;

@WebServlet("/SubmitReviewServlet")
public class SubmitReviewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            HttpSession session = request.getSession();
            String buyerEmail = (session != null) ? (String) session.getAttribute("userEmail") : null;
            
            if (buyerEmail == null) {
                response.sendRedirect("buyerLogin.html");
                return;
            }
            
            String productIdStr = request.getParameter("productId");
            String ratingStr = request.getParameter("rating");
            String comment = request.getParameter("comment");
            
            int productId = (productIdStr != null && !productIdStr.isEmpty()) ? Integer.parseInt(productIdStr) : 0;
            int rating = (ratingStr != null && !ratingStr.isEmpty()) ? Integer.parseInt(ratingStr) : 5;
            
            Connection conn = DBConnection.getConnection();
            if (conn != null) {
                String sql = "INSERT INTO product_reviews (product_id, buyer_email, rating, comment, review_date) VALUES (?, ?, ?, ?, NOW())";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, productId);
                pstmt.setString(2, buyerEmail);
                pstmt.setInt(3, rating);
                pstmt.setString(4, comment);
                
                pstmt.executeUpdate();
                pstmt.close();
                conn.close();
            }
            

            response.sendRedirect("orderHistory.jsp");

        } catch (Exception e) {
            e.printStackTrace();
           
            response.sendRedirect("orderHistory.jsp");
        }
    }
}