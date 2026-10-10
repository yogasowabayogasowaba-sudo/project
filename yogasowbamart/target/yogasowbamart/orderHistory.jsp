<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.yogasowbamart.model.Order" %>
<%@ page import="com.yogasowbamart.dao.OrderDAO" %>
<%
    String userEmail = (String) session.getAttribute("userEmail");
    
    if (userEmail == null) {
        response.sendRedirect("buyerLogin.html");
        return;
    }

    OrderDAO orderDAO = new OrderDAO();
    List<Order> orderList = orderDAO.getOrdersByUser(userEmail);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Order History - Yogasowbamart</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }
        .container { max-width: 1000px; background: white; margin: auto; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        h2 { text-align: center; color: #333; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 12px; border: 1px solid #ddd; text-align: center; }
        th { background-color: #4CAF50; color: white; }
        tr:nth-child(even) { background-color: #f9f9f9; }
        .review-btn { padding: 6px 12px; background-color: #007bff; color: white; text-decoration: none; border-radius: 4px; font-size: 14px; }
        .review-btn:hover { background-color: #0056b3; }
        .back-btn { display: inline-block; margin-top: 20px; padding: 10px 15px; background: #333; color: white; text-decoration: none; border-radius: 4px; }
        .back-btn:hover { background: #555; }
        .logout-btn { display: inline-block; margin-top: 20px; padding: 10px 15px; background: #dc3545; color: white; text-decoration: none; border-radius: 4px; }
        .logout-btn:hover { background: #c82333; }
    </style>
</head>
<body>

<div class="container">
    <h2>My Order History</h2>
    
    <% if (orderList == null || orderList.isEmpty()) { %>
        <p style="text-align: center; color: #777;">You have not placed any orders yet.</p>
    <% } else { %>
        <table>
            <thead>
                <tr>
                    <th>Order ID</th>
                    <th>Product Name</th>
                    <th>Quantity</th>
                    <th>Total Amount (₹)</th>
                    <th>Order Date</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <% for (Order order : orderList) { %>
                    <tr>
                        <td><%= order.getOrderId() %></td>
                        <td><%= order.getProductName() %></td>
                        <td><%= order.getQuantity() %></td>
                        <td>₹<%= order.getTotalAmount() %></td>
                        <td><%= order.getOrderDate() %></td>
                        <td><%= order.getStatus() %></td>
                        <td>
                            <!-- Write Review Button -->
                            <a href="writeReview.jsp?productId=<%= order.getProductId() %>" class="review-btn">Write Review</a>
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    <% } %>
    
    <div style="display: flex; justify-content: space-between; align-items: center;">
        <a href="home.html" class="back-btn">Back to Home</a>
        <a href="BuyerLogoutServlet" class="logout-btn">Logout</a>
    </div>
</div>

</body>
</html>