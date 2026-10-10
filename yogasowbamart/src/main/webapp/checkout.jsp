<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Checkout - Yoga Sowba Mart</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f6f9; padding: 40px; }
        .container { max-width: 600px; margin: auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); }
        h2 { color: #2563eb; text-align: center; margin-bottom: 25px; }
        .form-group { margin-bottom: 20px; }
        label { display: block; font-weight: bold; margin-bottom: 8px; color: #333; }
        input[type="text"], textarea { width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        textarea { resize: vertical; height: 80px; }
        .payment-options { margin-top: 10px; }
        .payment-options label { font-weight: normal; display: inline; margin-left: 5px; margin-right: 20px; }
        button { width: 100%; background-color: #16a34a; color: white; padding: 12px; border: none; border-radius: 4px; font-size: 16px; cursor: pointer; margin-top: 10px; }
        button:hover { background-color: #15803d; }
    </style>
</head>
<body>

<div class="container">
    <h2>Checkout Details</h2>
    
    <form action="OrderSuccessServlet" method="post">
        
        <%
            String[] products = request.getParameterValues("selectedProducts");
            if (products != null) {
                for (String p : products) {
        %>
                    <input type="hidden" name="selectedProducts" value="<%= p %>">
        <%
                }
            }
        %>

        <div class="form-group">
            <label for="fullName">Full Name:</label>
            <input type="text" id="fullName" name="fullName" required placeholder="Enter your full name">
        </div>

        <div class="form-group">
            <label for="mobileNumber">Mobile Number:</label>
            <input type="text" id="mobileNumber" name="mobileNumber" required placeholder="Enter mobile number">
        </div>

        <div class="form-group">
            <label for="address">Delivery Address:</label>
            <textarea id="address" name="address" required placeholder="Enter your delivery address"></textarea>
        </div>

        <div class="form-group">
            <label>Payment Method:</label>
            <div class="payment-options">
                <input type="radio" id="cod" name="paymentMethod" value="Cash on Delivery" checked>
                <label for="cod">Cash on Delivery (COD)</label>
                
                <input type="radio" id="online" name="paymentMethod" value="Online Payment">
                <label for="online">Online Payment</label>
            </div>
        </div>

        <button type="submit">Place Order</button>
    </form>
</div>

</body>
</html>