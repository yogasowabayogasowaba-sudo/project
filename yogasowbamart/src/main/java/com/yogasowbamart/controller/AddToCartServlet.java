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
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userEmail") == null) {
                response.sendRedirect("index.html");
                return;
            }
            
            String userEmail = (String) session.getAttribute("userEmail");
            
            String productName = request.getParameter("productName");
            double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            
            CartDAO cartDAO = new CartDAO();
            boolean isAdded = cartDAO.addToCart(userEmail, productName, price, quantity);

            if (isAdded) {
                // கார்ட்டுக்குப் போகாமல், ஹோம் பேஜுக்கே மெசேஜ் உடன் திரும்பப் போகும்
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