package com.yogasowbamart.controller;

import com.yogasowbamart.dao.CartDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/removeCart")
public class RemoveCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            response.sendRedirect("login.html");
            return;
        }

        String userEmail = (String) session.getAttribute("userEmail");
        String productName = request.getParameter("productName");

        if (productName != null && !productName.trim().isEmpty()) {
            CartDAO cartDAO = new CartDAO();
            cartDAO.removeFromCart(userEmail, productName);
        }

        response.sendRedirect("viewCart");
    }
}