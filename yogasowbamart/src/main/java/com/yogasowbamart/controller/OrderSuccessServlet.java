package com.yogasowbamart.controller;

import com.yogasowbamart.dao.OrderDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/OrderSuccessServlet")
public class OrderSuccessServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            response.sendRedirect("buyerLogin.html");
            return;
        }

        String userEmail = (String) session.getAttribute("userEmail");
        String fullName = request.getParameter("fullName");
        String mobileNumber = request.getParameter("mobileNumber");
        String address = request.getParameter("address");
        String paymentMethod = request.getParameter("paymentMethod");
        
        // Checkout பக்கத்தில் இருந்து வரும் 'selectedProducts'-ஐ 'selectedItems' பெயரில் பெறுகிறோம்
        String[] selectedItems = request.getParameterValues("selectedProducts");

        // OrderDAO மூலம் குறிப்பிட்ட பொருட்களை மட்டும் டேட்டாபேஸில் சேமிக்கிறோம்
        OrderDAO orderDAO = new OrderDAO();
        boolean isOrdered = orderDAO.placeOrder(userEmail, selectedItems);

        out.println("<html><head><title>Order Success</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background-color: #f4f6f9; padding: 40px; text-align: center; }");
        out.println(".box { background: white; max-width: 500px; margin: auto; padding: 30px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }");
        out.println("h2 { color: #16a34a; }");
        out.println("p { color: #555; font-size: 16px; line-height: 1.5; }");
        out.println("a { display: inline-block; margin-top: 20px; background-color: #2563eb; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; }");
        out.println("</style>");
        out.println("</head><body>");
        
        out.println("<div class='box'>");
        if (isOrdered) {
            out.println("<h2>Order Placed Successfully! 🎉</h2>");
            out.println("<p>Thank you, <b>" + (fullName != null ? fullName : "Customer") + "</b>!</p>");
            out.println("<p>Your order will be shipped to: <br><b>" + (address != null ? address : "") + "</b></p>");
            out.println("<p>Mobile: <b>" + (mobileNumber != null ? mobileNumber : "") + "</b></p>");
            out.println("<p>Payment Method: <b>" + (paymentMethod != null ? paymentMethod : "Cash on Delivery") + "</b></p>");
        } else {
            out.println("<h2 style='color: red;'>Order Failed!</h2>");
            out.println("<p>No products were selected or something went wrong.</p>");
        }
        out.println("<a href='home.html'>Back to Home</a>");
        out.println("</div>");
        
        out.println("</body></html>");
    }
}