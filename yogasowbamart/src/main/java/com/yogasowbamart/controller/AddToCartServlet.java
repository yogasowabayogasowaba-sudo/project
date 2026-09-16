package com.yogasowbamart.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yogasowbamart.dao.CartDAO;

@WebServlet("/addToCart")
public class AddToCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // 1. Check User Session
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userId") == null) {
                response.sendRedirect("index.html");
                return;
            }

            // 2. Get userId from Session and parameters from request
            int userId = (Integer) session.getAttribute("userId");
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            // 3. Call DAO to insert into database
            CartDAO cartDAO = new CartDAO();
            boolean isAdded = cartDAO.addToCart(userId, productId, quantity);

            if (isAdded) {
                response.sendRedirect("home.html?status=success");
            } else {
                response.sendRedirect("home.html?status=failed");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("home.html?status=error");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doPost(request, response);
    }
}