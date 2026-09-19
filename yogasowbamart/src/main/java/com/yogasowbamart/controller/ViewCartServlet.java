package com.yogasowbamart.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yogasowbamart.dao.CartDAO;

@WebServlet("/viewCart")
public class ViewCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            response.sendRedirect("index.html");
            return;
        }

        String userEmail = (String) session.getAttribute("userEmail");

        out.println("<html><head><title>Your Cart - YogasowbaMart</title>");
        out.println("<style>");
        out.println("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f8f9fa; margin: 40px; }");
        out.println("h2 { color: #1e293b; margin-bottom: 20px; }");
        out.println("table { width: 100%; max-width: 800px; border-collapse: collapse; background: white; box-shadow: 0 4px 6px rgba(0,0,0,0.1); border-radius: 8px; overflow: hidden; }");
        out.println("th, td { padding: 12px 15px; text-align: left; border-bottom: 1px solid #e2e8f0; }");
        out.println("th { background-color: #1e293b; color: white; }");
        out.println("tr:hover { background-color: #f1f5f9; }");
        out.println(".btn { display: inline-block; margin-top: 20px; margin-right: 10px; text-decoration: none; padding: 10px 18px; border-radius: 6px; font-weight: 600; }");
        out.println(".btn-primary { background-color: #0284c7; color: white; }");
        out.println(".btn-primary:hover { background-color: #0369a1; }");
        out.println(".btn-success { background-color: #16a34a; color: white; }");
        out.println(".btn-success:hover { background-color: #15803d; }");
        out.println("</style>");
        out.println("</head><body>");
        
        out.println("<h2>Your Shopping Cart (" + userEmail + ")</h2>");

        CartDAO cartDAO = new CartDAO();
        List<String[]> cartItems = cartDAO.getCartItems(userEmail);

        out.println("<table><tr><th>Product Name</th><th>Price (₹)</th><th>Quantity</th></tr>");

        if (cartItems.isEmpty()) {
            out.println("<tr><td colspan='3' style='text-align: center; color: #64748b;'>Your cart is empty!</td></tr>");
        } else {
            for (String[] item : cartItems) {
                out.println("<tr>");
                out.println("<td>" + item[0] + "</td>");
                out.println("<td>" + item[1] + "</td>");
                out.println("<td>" + item[2] + "</td>");
                out.println("</tr>");
            }
        }

        out.println("</table>");
        
        // Navigation Buttons: Continue Shopping & Proceed to Checkout
        out.println("<br>");
        out.println("<a href='home.html' class='btn btn-primary'>← Continue Shopping</a>");
        
        // கார்ட்டில் பொருட்கள் இருந்தால் மட்டும் Checkout பட்டனை காட்டுவது நலம்
        if (!cartItems.isEmpty()) {
            out.println("<a href='checkout' class='btn btn-success'>Proceed to Checkout →</a>");
        }
        
        out.println("</body></html>");
    }
}