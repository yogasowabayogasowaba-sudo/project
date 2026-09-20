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
            // லாகின் செய்த பயனர் (User) செஷனில் உள்ளாரா எனச் சரிபார்த்தல்
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("userEmail") == null) {
                response.sendRedirect("index.html");
                return;
            }
            
            String userEmail = (String) session.getAttribute("userEmail");
            
            // ஃபார்மில் இருந்து அனுப்பப்பட்ட தயாரிப்பு விவரங்களைப் பெறுதல்
            String productName = request.getParameter("productName");
            double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            
            // DAO மூலம் தரவுத்தளத்தில் பொருளைச் சேமித்தல்
            CartDAO cartDAO = new CartDAO();
            boolean isAdded = cartDAO.addToCart(userEmail, productName, price, quantity);

            // வெற்றிகரமாகச் சேர்ந்தால் ஹோம் பக்கத்திற்கே வெற்றிச் செய்தியுடன் திருப்புதல்
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