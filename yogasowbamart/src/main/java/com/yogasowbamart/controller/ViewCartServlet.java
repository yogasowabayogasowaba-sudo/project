package com.yogasowbamart.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/viewCart")
public class ViewCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private String dbUrl = "jdbc:h2:~/yogasowbamart;DB_CLOSE_DELAY=-1";
    private String dbUser = "sa";
    private String dbPass = "";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("index.html");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");

        out.println("<html><head><title>Your Cart - YogasowbaMart</title></head><body>");
        out.println("<h2>Your Shopping Cart</h2>");

        String query = "SELECT p.name, p.price, c.quantity FROM cart_items c " +
                       "JOIN products p ON c.product_id = p.id WHERE c.user_id = ?";

        try {
            Class.forName("org.h2.Driver");
            try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
                 PreparedStatement ps = conn.prepareStatement(query)) {
                
                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();

                out.println("<table border='1'><tr><th>Product</th><th>Price</th><th>Quantity</th></tr>");
                boolean hasItems = false;

                while (rs.next()) {
                    hasItems = true;
                    out.println("<tr>");
                    out.println("<td>" + rs.getString("name") + "</td>");
                    out.println("<td>" + rs.getDouble("price") + "</td>");
                    out.println("<td>" + rs.getInt("quantity") + "</td>");
                    out.println("</tr>");
                }

                if (!hasItems) {
                    out.println("<tr><td colspan='3'>Your cart is empty!</td></tr>");
                }

                out.println("</table>");
                out.println("<br><a href='home.html'>Continue Shopping</a>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>Error loading cart items.</p>");
        }

        out.println("</body></html>");
    }
}