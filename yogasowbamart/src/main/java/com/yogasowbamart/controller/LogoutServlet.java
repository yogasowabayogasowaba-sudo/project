package com.yogasowbamart.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // செஷனை (Session) அழிக்கவும்
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        
        // லாகின் பக்கத்திற்கு ரீடைரக்ட் செய்யவும்
        response.sendRedirect("adminLogin.jsp");
    }
}