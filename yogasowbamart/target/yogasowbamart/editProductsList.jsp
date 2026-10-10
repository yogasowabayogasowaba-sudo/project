<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.Map" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Products - Yogashowba Mart</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f9; padding: 20px; }
        .container { max-width: 800px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0px 0px 10px rgba(0,0,0,0.1); }
        h2 { text-align: center; color: #333; margin-bottom: 20px; }
        .product-card { background: #fff; border: 1px solid #ddd; border-radius: 6px; padding: 15px; margin-bottom: 15px; display: flex; justify-content: space-between; align-items: center; box-shadow: 0 2px 4px rgba(0,0,0,0.05); }
        .product-info h3 { margin: 0 0 5px 0; color: #007bff; }
        .product-info p { margin: 3px 0; color: #555; font-size: 14px; }
        .edit-btn { background: #28a745; color: white; padding: 8px 15px; text-decoration: none; border-radius: 4px; font-weight: bold; }
        .edit-btn:hover { background: #218838; }
        .no-products { text-align: center; color: #777; font-size: 18px; margin-top: 30px; }
        .back-btn { display: inline-block; margin-top: 20px; text-decoration: none; background: #6c757d; color: white; padding: 10px 15px; border-radius: 4px; }
        .back-btn:hover { background: #5a6268; }
    </style>
</head>
<body>

<div class="container">
    <h2>Select a Product to Edit</h2>

    <%
        List<Map<String, String>> productList = (List<Map<String, String>>) request.getAttribute("productList");
        if (productList != null && !productList.isEmpty()) {
            for (Map<String, String> product : productList) {
    %>
                <div class="product-card">
                    <div class="product-info">
                        <h3><%= product.get("name") %></h3>
                        <p><strong>Price:</strong> ₹<%= product.get("price") %> | <strong>Category:</strong> <%= product.get("category") %> | <strong>Stock:</strong> <%= product.get("stock") %></p>
                        <p><%= product.get("description") %></p>
                    </div>
                    <div>
                        <a href="editProductForm?id=<%= product.get("id") %>" class="edit-btn">Edit</a>
                    </div>
                </div>
    <%
            }
        } else {
    %>
            <div class="no-products">No products available to edit.</div>
    <%
        }
    %>

    <a href="sellerDashboard.html" class="back-btn">Back to Dashboard</a>
</div>

</body>
</html>