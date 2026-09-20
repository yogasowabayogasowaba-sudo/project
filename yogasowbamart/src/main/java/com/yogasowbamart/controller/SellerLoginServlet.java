package com.yogasowbamart.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/sellerLogin")
public class SellerLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        if (email != null && !email.trim().isEmpty()) {
            HttpSession session = request.getSession();
            session.setAttribute("sellerEmail", email);
            response.sendRedirect("sellerDashboard.html"); // சேல்ஸ் டேஷ்போர்டு பக்கம்
        } else {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("<h3 style='color:red; text-align:center;'>Please enter a valid email! <a href='sellerLogin.html'>Try Again</a></h3>");
        }
    }
}