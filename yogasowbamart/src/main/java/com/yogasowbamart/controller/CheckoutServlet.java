package com.yogasowbamart.controller;

import com.yogasowbamart.dao.CartDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            response.sendRedirect("buyerLogin.html");
            return;
        }

        String userEmail = (String) session.getAttribute("userEmail");
        String[] selectedItems = request.getParameterValues("selectedProducts");

        out.println("<html><head><title>Checkout Details</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background-color: #f4f6f9; margin: 0; padding: 20px; }");
        out.println(".container { max-width: 800px; margin: auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }");
        out.println("h2, h3 { color: #333; }");
        out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
        out.println("th, td { padding: 12px; border: 1px solid #ddd; text-align: left; }");
        out.println("th { background-color: #2c3e50; color: white; }");
        out.println(".form-group { margin-top: 15px; }");
        out.println(".form-group label { display: block; margin-bottom: 5px; font-weight: bold; color: #555; }");
        out.println(".form-group input[type='text'] { width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }");
        out.println(".btn-order { background-color: #16a34a; color: white; padding: 12px 25px; border: none; border-radius: 6px; font-size: 16px; font-weight: bold; cursor: pointer; margin-top: 20px; width: 100%; }");
        out.println(".btn-order:hover { background-color: #15803d; }");
        out.println("</style>");
        out.println("</head><body>");

        out.println("<div class='container'>");
        out.println("<h2>Checkout Details</h2>");
        out.println("<h3>Selected Products Summary</h3>");

        if (selectedItems == null || selectedItems.length == 0) {
            out.println("<p style='color: red;'>No products selected for checkout.</p>");
            out.println("<br><a href='viewCart'>← Back to Cart</a>");
        } else {
            CartDAO cartDAO = new CartDAO();
            List<String[]> allCartItems = cartDAO.getCartItems(userEmail);

            out.println("<table>");
            out.println("<tr><th>Product Name</th><th>Price (₹)</th><th>Quantity</th></tr>");

            double totalAmount = 0.0;

            for (String val : selectedItems) {
                String[] parts = val.split("_");
                if (parts.length < 2) continue;
                
                int itemIndex;
                try {
                    itemIndex = Integer.parseInt(parts[parts.length - 1]);
                } catch (NumberFormatException e) {
                    continue;
                }

                if (itemIndex >= 0 && itemIndex < allCartItems.size()) {
                    String[] item = allCartItems.get(itemIndex);

                    out.println("<tr>");
                    out.println("<td>" + item[0] + "</td>");
                    out.println("<td>" + item[1] + "</td>");
                    out.println("<td>" + item[2] + "</td>");
                    out.println("</tr>");

                    try {
                        double price = Double.parseDouble(item[1]);
                        int qty = Integer.parseInt(item[2]);
                        totalAmount += (price * qty);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            out.println("</table>");
            out.println("<h3 style='text-align: right; margin-top: 15px;'>Total Amount: ₹ " + totalAmount + "</h3>");

            // Delivery Details & Payment Form
            out.println("<hr style='margin: 25px 0; border: 0; border-top: 1px solid #ddd;'>");
            out.println("<h3>Delivery Details</h3>");
            out.println("<form action='OrderSuccessServlet' method='post'>");
            
            // மிக முக்கியம்: செலக்ட் செய்த பொருட்களை hidden inputs மூலம் அடுத்த serv-க்கு அனுப்புகிறோம்
            for (String val : selectedItems) {
                out.println("<input type='hidden' name='selectedProducts' value='" + val + "'>");
            }

            out.println("<div class='form-group'>");
            out.println("<label>Full Name:</label>");
            out.println("<input type='text' name='fullName' required placeholder='Enter your full name'>");
            out.println("</div>");

            out.println("<div class='form-group'>");
            out.println("<label>Mobile Number:</label>");
            out.println("<input type='text' name='mobileNumber' required placeholder='Enter 10-digit mobile number'>");
            out.println("</div>");

            out.println("<div class='form-group'>");
            out.println("<label>Delivery Address:</label>");
            out.println("<input type='text' name='address' required placeholder='Enter delivery address'>");
            out.println("</div>");

            out.println("<div class='form-group'>");
            out.println("<label>Payment Method:</label>");
            out.println("<input type='radio' name='paymentMethod' value='Cash on Delivery' checked> Cash on Delivery (COD) &nbsp;&nbsp;");
            out.println("<input type='radio' name='paymentMethod' value='Online Payment'> Online Payment");
            out.println("</div>");

            out.println("<button type='submit' class='btn-order'>Place Order</button>");
            out.println("</form>");
        }

        out.println("</div>");
        out.println("</body></html>");
    }
}