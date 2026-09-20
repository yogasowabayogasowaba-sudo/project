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
import com.yogasowbamart.model.Order;
import com.yogasowbamart.dao.OrderDAO;

@WebServlet("/orderHistory")
public class OrderHistoryServlet extends HttpServlet {
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
        OrderDAO orderDAO = new OrderDAO();
        List<Order> orders = orderDAO.getOrdersByUser(userEmail);

        out.println("<html><head><title>My Orders - YogasowbaMart</title>");
        out.println("<style>");
        out.println("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f8f9fa; margin: 30px; }");
        out.println(".container { max-width: 900px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); }");
        out.println("h2 { color: #0284c7; margin-bottom: 20px; text-align: center; }");
        out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
        out.println("th, td { padding: 12px; border: 1px solid #cbd5e1; text-align: left; }");
        out.println("th { background-color: #f1f5f9; color: #334155; }");
        out.println("tr:nth-child(even) { background-color: #f8fafc; }");
        out.println(".btn { display: inline-block; margin-top: 20px; text-decoration: none; background-color: #475569; color: white; padding: 10px 20px; border-radius: 6px; font-weight: 600; }");
        out.println(".btn:hover { background-color: #334155; }");
        out.println("</style>");
        out.println("</head><body>");
        
        out.println("<div class='container'>");
        out.println("<h2>My Order History 📦</h2>");
        out.println("<p>Logged in as: <strong>" + userEmail + "</strong></p>");
        
        if (orders == null || orders.isEmpty()) {
            out.println("<p style='text-align:center; color:#64748b; margin-top:30px;'>You have not placed any orders yet.</p>");
        } else {
            out.println("<table>");
            out.println("<tr><th>Order ID</th><th>Product Name</th><th>Quantity</th><th>Total Amount</th><th>Order Date</th><th>Status</th></tr>");
            for (Order o : orders) {
                out.println("<tr>");
                out.println("<td>#" + o.getOrderId() + "</td>");
                out.println("<td>" + o.getProductName() + "</td>");
                out.println("<td>" + o.getQuantity() + "</td>");
                out.println("<td>₹" + o.getTotalAmount() + "</td>");
                out.println("<td>" + o.getOrderDate() + "</td>");
                out.println("<td><span style='color: #d97706; font-weight: bold;'>" + o.getStatus() + "</span></td>");
                out.println("</tr>");
            }
            out.println("</table>");
        }
        
        out.println("<br><a href='home.html' class='btn'>Return to Home</a>");
        out.println("</div>");
        
        out.println("</body></html>");
    }
}