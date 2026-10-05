<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Update Product - Yogashowba Mart</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f9; padding: 20px; }
        .container { max-width: 500px; margin: 0 auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0px 0px 10px rgba(0,0,0,0.1); }
        h2 { text-align: center; color: #333; margin-bottom: 20px; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; color: #555; }
        input[type="text"], input[type="number"], textarea { width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        textarea { resize: vertical; height: 100px; }
        .submit-btn { background: #28a745; color: white; padding: 10px 15px; border: none; border-radius: 4px; width: 100%; font-size: 16px; font-weight: bold; cursor: pointer; }
        .submit-btn:hover { background: #218838; }
        .back-btn { display: inline-block; margin-top: 15px; text-align: center; width: 100%; text-decoration: none; color: #6c757d; }
    </style>
</head>
<body>

<div class="container">
    <h2>Edit Product Details</h2>

    <%
        Map<String, String> product = (Map<String, String>) request.getAttribute("product");
        if (product != null) {
    %>
        <form action="updateProduct" method="post">
            <input type="hidden" name="id" value="<%= product.get("id") %>">

            <div class="form-group">
                <label>Product Name:</label>
                <input type="text" name="name" value="<%= product.get("name") %>" required>
            </div>

            <div class="form-group">
                <label>Description:</label>
                <textarea name="description" required><%= product.get("description") %></textarea>
            </div>

            <div class="form-group">
                <label>Price (₹):</label>
                <input type="number" step="0.01" name="price" value="<%= product.get("price") %>" required>
            </div>

            <div class="form-group">
                <label>Stock:</label>
                <input type="number" name="stock" value="<%= product.get("stock") %>" required>
            </div>

            <div class="form-group">
                <label>Category:</label>
                <input type="text" name="category" value="<%= product.get("category") %>" required>
            </div>

            <button type="submit" class="submit-btn">Update Product</button>
        </form>
    <%
        } else {
    %>
        <p style="text-align: center; color: red;">Product not found!</p>
    <%
        }
    %>

    <a href="editProductsList" class="back-btn">Back to Products List</a>
</div>

</body>
</html>