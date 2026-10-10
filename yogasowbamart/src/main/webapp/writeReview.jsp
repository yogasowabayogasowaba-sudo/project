<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String userEmail = (String) session.getAttribute("userEmail");
    if (userEmail == null) {
        response.sendRedirect("buyerLogin.html");
        return;
    }
    
    String productIdStr = request.getParameter("productId");
    int productId = 0;
    try {
        if (productIdStr != null) {
            productId = Integer.parseInt(productIdStr);
        }
    } catch (NumberFormatException e) {
        productId = 0;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Write Product Review - Yogasowbamart</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }
        .container { max-width: 600px; background: white; margin: auto; padding: 30px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        h2 { text-align: center; color: #333; margin-bottom: 20px; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 8px; font-weight: bold; color: #555; }
        select, textarea { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; font-size: 14px; box-sizing: border-box; }
        textarea { resize: vertical; height: 120px; }
        .submit-btn { width: 100%; background-color: #4CAF50; color: white; padding: 12px; border: none; border-radius: 4px; font-size: 16px; cursor: pointer; }
        .submit-btn:hover { background-color: #45a049; }
        .back-link { display: block; text-align: center; margin-top: 15px; color: #007bff; text-decoration: none; }
        .back-link:hover { text-decoration: underline; }
    </style>
</head>
<body>

<div class="container">
    <h2>Write Review for Product</h2>
    
    <form action="SubmitReviewServlet" method="POST">
        
        <input type="hidden" name="productId" value="<%= productId %>">
        
        <div class="form-group">
            <label for="rating">Rating:</label>
            <select name="rating" id="rating" required>
                <option value="">Select Rating</option>
                <option value="5">⭐⭐⭐⭐⭐ (5 - Excellent)</option>
                <option value="4">⭐⭐⭐⭐ (4 - Very Good)</option>
                <option value="3">⭐⭐⭐ (3 - Good)</option>
                <option value="2">⭐⭐ (2 - Fair)</option>
                <option value="1">⭐ (1 - Poor)</option>
            </select>
        </div>
        
        <div class="form-group">
            <label for="comment">Comments:</label>
            <textarea name="comment" id="comment" placeholder="Write your review about this product here..." required></textarea>
        </div>
        
        <button type="submit" class="submit-btn">Submit Review</button>
    </form>
    
    <a href="orderHistory.jsp" class="back-link">Back to Order History</a>
</div>

</body>
</html>