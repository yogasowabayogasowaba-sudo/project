<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Successful</title>
    <style>
        body { 
            font-family: Arial, sans-serif; 
            text-align: center; 
            margin-top: 50px; 
            background-color: #f9f9f9; 
        }
        .success-box { 
            background: white; 
            padding: 30px; 
            border-radius: 8px; 
            box-shadow: 0px 0px 10px rgba(0,0,0,0.1); 
            display: inline-block; 
        }
        h2 { 
            color: #28a745; 
        }
        .btn { 
            display: inline-block; 
            margin-top: 15px; 
            padding: 10px 20px; 
            background: #007bff; 
            color: white; 
            text-decoration: none; 
            border-radius: 5px; 
        }
        .btn:hover {
            background: #0056b3;
        }
    </style>
</head>
<body>
    <div class="success-box">
        <h2>Order Placed Successfully! 🎉</h2>
        <p>Thank you for shopping with us. Your order has been placed successfully.</p>
        
        <a href="home.html" class="btn">Back to Home</a>
    </div>
</body>
</html>