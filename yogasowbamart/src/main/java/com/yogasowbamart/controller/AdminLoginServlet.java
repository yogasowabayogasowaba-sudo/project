package com.yogasowbamart.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        
        String adminUser = "admin";
        String adminPass = "admin123";
        
        if (username != null && username.equals(adminUser) && password != null && password.equals(adminPass)) {
           
            HttpSession session = request.getSession();
            session.setAttribute("adminSession", username);
            

            response.sendRedirect("adminDashboard.jsp");
        } else {
           
            response.sendRedirect("adminLogin.jsp?error=invalid");
        }
    }
}