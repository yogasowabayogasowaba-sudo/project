package com.yogasowbamart.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/orderSuccess")
public class OrderSuccessServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            response.sendRedirect("index.html");
            return;
        }

        String userEmail = (String) session.getAttribute("userEmail");

        out.println("<html><head><title>Order Success - YogasowbaMart</title>");
        out.println("<style>");
        out.println("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f8f9fa; margin: 40px; text-align: center; }");
        out.println(".container { max-width: 600px; margin: 0 auto; background: white; padding: 40px; border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); }");
        out.println("h2 { color: #16a34a; margin-bottom: 20px; }");
        out.println("p { color: #475569; font-size: 1.1em; }");
        out.println(".btn { display: inline-block; margin-top: 30px; text-decoration: none; background-color: #0284c7; color: white; padding: 12px 24px; border-radius: 6px; font-weight: 600; }");
        out.println(".btn:hover { background-color: #0369a1; }");
        out.println("</style>");
        out.println("</head><body>");
        
        out.println("<div class='container'>");
        out.println("<h2>Order Placed Successfully! 🎉</h2>");
        out.println("<p>Thank you for shopping with us, <strong>" + userEmail + "</strong>.</p>");
        out.println("<p>Your order has been placed successfully and will be delivered soon.</p>");
        out.println("<a href='home.html' class='btn'>Return to Home</a>");
        out.println("</div>");
        
        out.println("</body></html>");
    }
}