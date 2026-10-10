<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.yogasowbamart.model.Order" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Seller Orders - Yogasowbamart</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f7f6;
            margin: 0;
            padding: 20px;
        }
        h2 {
            text-align: center;
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        th, td {
            padding: 12px 15px;
            text-align: center;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #4CAF50;
            color: white;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
        .no-orders {
            text-align: center;
            color: #777;
            margin-top: 30px;
            font-size: 18px;
        }
    </style>
</head>
<body>

    <h2>Customer Orders Received</h2>

    <%
        List<Order> orderList = (List<Order>) request.getAttribute("orderList");
        if (orderList != null && !orderList.isEmpty()) {
    %>
        <table>
            <thead>
                <tr>
                    <th>Order ID</th>
                    <th>Customer Email</th>
                    <th>Product Name</th>
                    <th>Quantity</th>
                    <th>Total Amount (₹)</th>
                    <th>Order Date</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <% for (Order order : orderList) { %>
                    <tr>
                        <td><%= order.getOrderId() %></td>
                        <td><%= order.getUserEmail() %></td>
                        <td><%= order.getProductName() %></td>
                        <td><%= order.getQuantity() %></td>
                        <td><%= order.getTotalAmount() %></td>
                        <td><%= order.getOrderDate() %></td>
                        <td><%= order.getStatus() %></td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    <% } else { %>
        <div class="no-orders">No orders found!</div>
    <% } %>

</body>
</html>