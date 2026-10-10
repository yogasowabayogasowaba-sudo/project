<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.yogasowbamart.dao.ProductDAO" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin - Manage Products | Yoga Sowba Mart</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f6f9; margin: 0; padding: 20px; }
        .container { max-width: 1200px; margin: auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        h2 { color: #333; text-align: center; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 10px; border: 1px solid #ddd; text-align: center; font-size: 14px; }
        th { background-color: #ffc107; color: #333; }
        tr:nth-child(even) { background-color: #f9f9f9; }
        .back-btn { display: inline-block; margin-bottom: 15px; padding: 8px 15px; background-color: #6c757d; color: white; text-decoration: none; border-radius: 4px; }
        .back-btn:hover { background-color: #5a6268; }
        .action-btn { padding: 5px 10px; margin: 2px; text-decoration: none; border-radius: 3px; color: white; font-size: 12px; }
        .delete-btn { background-color: #dc3545; }
        .delete-btn:hover { background-color: #c82333; }
    </style>
</head>
<body>

    <div class="container">
        <a href="adminDashboard.jsp" class="back-btn">&larr; Back to Dashboard</a>
        <h2>Manage Products (All Sellers)</h2>
        
        <table>
            <thead>
                <tr>
                    <th>Product ID</th>
                    <th>Product Name</th>
                    <th>Description</th>
                    <th>Price (₹)</th>
                    <th>Stock</th>
                    <th>Category</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    ProductDAO productDao = new ProductDAO();
                    List<Map<String, String>> productsList = productDao.getAllProducts();
                    
                    if (productsList != null && !productsList.isEmpty()) {
                        for (Map<String, String> prod : productsList) {
                %>
                <tr>
                    <td><%= prod.get("id") %></td>
                    <td><%= prod.get("name") %></td>
                    <td><%= prod.get("description") %></td>
                    <td>₹<%= prod.get("price") %></td>
                    <td><%= prod.get("stock") %></td>
                    <td><%= prod.get("category") %></td>
                    <td>
                        <a href="DeleteProduct?id=<%= prod.get("id") %>" class="action-btn delete-btn" onclick="return confirm('Are you sure you want to delete this product?');">Delete</a>
                    </td>
                </tr>
                <% 
                        }
                    } else { 
                %>
                <tr>
                    <td colspan="7" style="text-align: center; color: #777;">No products found.</td>
                </tr>
                <% 
                    } 
                %>
            </tbody>
        </table>
    </div>

</body>
</html>