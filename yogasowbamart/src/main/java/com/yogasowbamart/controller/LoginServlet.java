package com.yogasowbamart.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // ஃபார்மில் இருந்து ஈமெயில் மற்றும் பாஸ்வேர்டைப் பெறுதல்
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // ஈமெயில் '@gmail.com'-ல் முடிகிறதா எனச் சரிபார்த்தல் (பாஸ்வேர்ட் என்னவாக இருந்தாலும் அனுமதிக்கும்)
        if (email != null && email.endsWith("@gmail.com")) {
            // லாகின் வெற்றி - ஹோம் பேஜுக்குச் செல்லும்
            response.sendRedirect(request.getContextPath() + "/home.html");
        } else {
            // லாகின் தோல்வி - இன்வேலிட் என்று காட்டும்
            response.sendRedirect("index.html?status=failed");
        }
    }
}