package com.yogasowbamart.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.yogasowbamart.dao.OrderDAO;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            response.sendRedirect(request.getContextPath() + "/login.html");
            return;
        }

        String userEmail = (String) session.getAttribute("userEmail");
        OrderDAO orderDAO = new OrderDAO();
        
        // கார்ட்டில் உள்ளதை ஆர்டராக மாற்றுதல்
        boolean isOrdered = orderDAO.placeOrder(userEmail);

        if (isOrdered) {
            // ஆர்டர் வெற்றி பெற்றால் Success Page-ஐ நேரடியாகக் காட்டுதல்
            out.println("<html><head><title>Order Successful</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; background-color: #f4f6f9; margin: 0; padding: 40px; text-align: center; }");
            out.println(".container { max-width: 600px; margin: auto; background: white; padding: 40px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }");
            out.println("h2 { color: #16a34a; margin-bottom: 10px; }");
            out.println("p { color: #555; font-size: 16px; }");
            out.println(".btn-home { background-color: #2c3e50; color: white; padding: 12px 25px; border: none; border-radius: 6px; font-size: 16px; font-weight: bold; cursor: pointer; text-decoration: none; display: inline-block; margin-top: 20px; }");
            out.println(".btn-home:hover { background-color: #1a252f; }");
            out.println("</style></head><body>");
            
            out.println("<div class='container'>");
            out.println("<h2>🎉 Order Placed Successfully!</h2>");
            out.println("<p>Thank you for shopping with us, <b>" + userEmail + "</b>.</p>");
            out.println("<p>Your order has been confirmed and placed successfully.</p>");
            out.println("<br>");
            out.println("<a href='" + request.getContextPath() + "/home.html' class='btn-home'>Return to Home</a>");
            out.println("</div>");
            
            out.println("</body></html>");
        } else {
            // கார்ட் காலியாக இருந்தால் அல்லது பிழை ஏற்பட்டால்
            response.sendRedirect(request.getContextPath() + "/viewCart");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doPost(request, response);
    }
}