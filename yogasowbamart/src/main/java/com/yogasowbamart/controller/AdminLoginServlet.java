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
        
        // அட்மனுக்கான யூசர்நேம் மற்றும் பாஸ்வேர்ட் (இதை நீங்கள் மாற்றிக்கொள்ளலாம்)
        String adminUser = "admin";
        String adminPass = "admin123";
        
        if (username != null && username.equals(adminUser) && password != null && password.equals(adminPass)) {
            // செஷன் கிரியேட் செய்தல்
            HttpSession session = request.getSession();
            session.setAttribute("adminSession", username);
            
            // அட்மின் டேஷ்போர்டுக்கு ரீடைரக்ட் செய்தல்
            response.sendRedirect("adminDashboard.jsp");
        } else {
            // தவறான விவரங்கள் என்றால் எரருடன் மீண்டும் லாகின் பக்கத்திற்கே அனுப்புவது
            response.sendRedirect("adminLogin.jsp?error=invalid");
        }
    }
}