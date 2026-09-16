package com.yogasowbamart.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. பிரவுசர் லாகின் பார்மில் இருந்து Email மற்றும் Password-ஐ வாங்குதல்
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        // 2. டேட்டாபேஸில் இந்த email மற்றும் password சரியა என செக் செய்தல்
        boolean isValidUser = false;
        int userId = -1;
        
        if ("admin@yogasowbamart.com".equals(email) && "123456".equals(password)) {
            isValidUser = true;
            userId = 1; 
        }

        // 3. ரிசல்ட்டுக்கு ஏற்ப ரீடிரக்ட் செய்தல்
        if (isValidUser) {
            // லாகின் வெற்றி: Session-ல் userId-ஐ செட் செய்துவிட்டு ஹோம் பேஜுக்கு அனுப்பவும்
            HttpSession session = request.getSession();
            session.setAttribute("userId", userId);
            response.sendRedirect("home.html");
        } else {
            // லாகின் தோல்வி: மீண்டும் index.html-க்கே அனுப்பவும்
            response.sendRedirect("index.html?status=failed");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doPost(request, response);
    }
}