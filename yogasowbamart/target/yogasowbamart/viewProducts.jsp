<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.Map" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>My Products - Yogashowba Mart</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f9; padding: 20px; }
        .container { max-width: 800px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0px 0px 10px rgba(0,0,0,0.1); }
        h2 { text-align: center; color: #333; margin-bottom: 20px; }
        .product-card { background: #fff; border: 1px solid #ddd; border-radius: 6px; padding: 15px; margin-bottom: 15px; box-shadow: 0 2px 4px rgba(0,0,0,0.05); }
        .product-card h3 { margin: 0 0 10px 0; color: #007bff; }
        .product-card p { margin: 5px 0; color: #555; }
        .no-products { text-align: center; color: #777; font-size: 18px; margin-top: 30px; }
        .back-btn { display: inline-block; margin-top: 20px; text-decoration: none; background: #6c757d; color: white; padding: 10px 15px; border-radius: 4px; }
        .back-btn:hover { background: #5a6268; }
    </style>
</head>
<body>

<div class="container">
    <h2>My Products</h2>

    <%
        List<Map<String, String>> productList = (List<Map<String, String>>) request.getAttribute("productList");
        if (productList != null && !productList.isEmpty()) {
            for (Map<String, String> product : productList) {
    %>
                <div class="product-card">
                    <h3><%= product.get("name") %></h3>
                    <p><strong>Description:</strong> <%= product.get("description") %></p>
                    <p><strong>Price:</strong> ₹<%= product.get("price") %> &nbsp;|&nbsp; <strong>Category:</strong> <%= product.get("category") %> &nbsp;|&nbsp; <strong>Stock:</strong> <%= product.get("stock") %></p>
                    
                    <!-- Edit ken Delete buttons -->
                    <div style="margin-top: 10px;">
                        <!-- Naurnos a link a mangiturong iti servlet imbes a direkta iti JSP -->
                        <a href="editProductForm?id=<%= product.get("id") %>" style="background: #ffc107; color: #000; padding: 6px 12px; text-decoration: none; border-radius: 4px; margin-right: 8px; font-size: 14px;">Edit</a>
                        <a href="deleteProduct?id=<%= product.get("id") %>" style="background: #dc3545; color: #fff; padding: 6px 12px; text-decoration: none; border-radius: 4px; font-size: 14px;" onclick="return confirm('Are you sure you want to delete this product?');">Delete</a>
                    </div>
                </div>
    <%
            }
        } else {
    %>
            <div class="no-products">No products added yet.</div>
    <%
        }
    %>

    <a href="sellerDashboard.html" class="back-btn">Back to Dashboard</a>
</div>

</body>
</html>