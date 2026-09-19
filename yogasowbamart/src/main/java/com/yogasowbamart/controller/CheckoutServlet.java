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

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
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

        CartDAO cartDAO = new CartDAO();
        List<String[]> cartItems = cartDAO.getCartItems(userEmail);

        // ஒருவேளை கார்ட் காலியாக இருந்தால் கார்ட் பக்கத்திற்கே திருப்பி அனுப்பிவிடலாம்
        if (cartItems.isEmpty()) {
            response.sendRedirect("viewCart");
            return;
        }

        // மொத்தத் தொகையைக் கணக்கிடுதல் (Calculation)
        double grandTotal = 0.0;
        for (String[] item : cartItems) {
            try {
                double price = Double.parseDouble(item[1]);
                int qty = Integer.parseInt(item[2]);
                grandTotal += (price * qty);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        out.println("<html><head><title>Checkout - YogasowbaMart</title>");
        out.println("<style>");
        out.println("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f8f9fa; margin: 40px; }");
        out.println(".container { max-width: 600px; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); }");
        out.println("h2 { color: #1e293b; margin-bottom: 20px; }");
        out.println(".summary-item { display: flex; justify-content: space-between; padding: 10px 0; border-bottom: 1px solid #e2e8f0; }");
        out.println(".total { font-size: 1.2em; font-weight: bold; color: #16a34a; margin-top: 15px; }");
        out.println(".btn { display: inline-block; margin-top: 20px; text-decoration: none; background-color: #16a34a; color: white; padding: 12px 20px; border-radius: 6px; font-weight: 600; border: none; cursor: pointer; width: 100%; text-align: center; }");
        out.println(".btn:hover { background-color: #15803d; }");
        out.println("</style>");
        out.println("</head><body>");
        
        out.println("<div class='container'>");
        out.println("<h2>Order Checkout</h2>");
        out.println("<p>Logged in as: <strong>" + userEmail + "</strong></p>");
        out.println("<hr style='border: 0; border-top: 1px solid #e2e8f0; margin: 20px 0;'>");
        
        out.println("<h3>Order Summary</h3>");
        for (String[] item : cartItems) {
            out.println("<div class='summary-item'>");
            out.println("<span>" + item[0] + " (Qty: " + item[2] + ")</span>");
            out.println("<span>₹ " + (Double.parseDouble(item[1]) * Integer.parseInt(item[2])) + "</span>");
            out.println("</div>");
        }

        out.println("<div class='total summary-item'>");
        out.println("<span>Grand Total:</span>");
        out.println("<span>₹ " + grandTotal + "</span>");
        out.println("</div>");

        // ஆர்டரை உறுதிப்படுத்தும் பட்டன் (Order Place பண்றதுக்கு)
        out.println("<form action='orderSuccess' method='POST'>");
        out.println("<button type='submit' class='btn'>Place Order Now</button>");
        out.println("</form>");
        
        out.println("</div>");
        out.println("</body></html>");
    }
}