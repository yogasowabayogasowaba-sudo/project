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

@WebServlet("/viewCart")
public class ViewCartServlet extends HttpServlet {
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
        CartDAO cartDAO = new CartDAO();
        List<String[]> cartItems = cartDAO.getCartItems(userEmail);

        out.println("<html><head><title>Your Shopping Cart</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background-color: #f4f6f9; margin: 0; padding: 20px; }");
        out.println(".container { max-width: 800px; margin: auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }");
        out.println("h2 { color: #333; text-align: center; }");
        out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
        out.println("th, td { padding: 12px; border: 1px solid #ddd; text-align: left; }");
        out.println("th { background-color: #2c3e50; color: white; }");
        out.println(".btn-checkout { background-color: #16a34a; color: white; padding: 12px 25px; border: none; border-radius: 6px; font-size: 16px; font-weight: bold; cursor: pointer; text-decoration: none; display: inline-block; }");
        out.println(".btn-checkout:hover { background-color: #15803d; }");
        out.println(".btn-home { background-color: #64748b; color: white; padding: 12px 25px; border-radius: 6px; font-size: 16px; font-weight: bold; text-decoration: none; display: inline-block; }");
        out.println(".btn-home:hover { background-color: #475569; }");
        out.println("</style>");
        
        // JavaScript validation
        out.println("<script>");
        out.println("function validateForm() {");
        out.println("  var checkboxes = document.querySelectorAll('input[name=\"selectedProducts\"]:checked');");
        out.println("  if (checkboxes.length === 0) {");
        out.println("    alert('Please select at least one product to proceed to checkout.');");
        out.println("    return false;");
        out.println("  }");
        out.println("  return true;");
        out.println("}");
        out.println("</script>");
        
        out.println("</head><body>");

        out.println("<div class='container'>");
        out.println("<h2>Your Shopping Cart (" + userEmail + ")</h2>");

        if (cartItems.isEmpty()) {
            out.println("<p style='text-align:center; color: #666; margin-top: 30px;'>Your cart is currently empty.</p>");
            out.println("<div style='text-align: center; margin-top: 20px;'>");
            out.println("<a href='home.html' class='btn-home'>← Return to Home</a>");
            out.println("</div>");
        } else {
            // 404 எரரைத் தவிர்க்க முழுமையான Context Path உடன் form action கொடுக்கப்பட்டுள்ளது
            out.println("<form action='" + request.getContextPath() + "/checkout' method='get' onsubmit='return validateForm();'>");
            out.println("<table>");
            out.println("<tr><th>Select</th><th>Product Name</th><th>Price (₹)</th><th>Quantity</th></tr>");
            
            int index = 0;
            for (String[] item : cartItems) {
                out.println("<tr>");
                out.println("<td style='text-align:center;'><input type='checkbox' name='selectedProducts' value='" + item[0] + "_" + index + "' checked></td>");
                out.println("<td>" + item[0] + "</td>");
                out.println("<td>" + item[1] + "</td>");
                out.println("<td>" + item[2] + "</td>");
                out.println("</tr>");
                index++;
            }
            out.println("</table>");
            
            out.println("<div style='display: flex; justify-content: space-between; align-items: center; margin-top: 20px;'>");
            out.println("<a href='home.html' class='btn-home'>← Return to Home</a>");
            out.println("<button type='submit' class='btn-checkout'>Proceed to Checkout →</button>");
            out.println("</div>");
            out.println("</form>");
        }

        out.println("</div>");
        out.println("</body></html>");
    }
}