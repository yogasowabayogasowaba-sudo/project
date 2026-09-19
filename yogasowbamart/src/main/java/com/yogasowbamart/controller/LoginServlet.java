package com.yogasowbamart.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email != null && email.endsWith("@gmail.com")) {
            HttpSession session = request.getSession();
            // email-ஐ செஷனில் சேமிக்கிறோம்
            session.setAttribute("userEmail", email);
            response.sendRedirect(request.getContextPath() + "/home.html");
        } else {
            response.sendRedirect("index.html?status=failed");
        }
    }
}