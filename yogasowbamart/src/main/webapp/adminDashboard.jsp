<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // செஷன் சரிபார்த்தல் (அட்மின் லாகின் செய்யாமல் உள்ளே வர முடியாது)
    if (session.getAttribute("adminSession") == null) {
        response.sendRedirect("adminLogin.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - Yogasowbamart</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f7f6;
            margin: 0;
            padding: 0;
        }
        .header {
            background-color: #343a40;
            color: white;
            padding: 15px 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .header h1 {
            margin: 0;
            font-size: 24px;
        }
        .logout-btn {
            background-color: #dc3545;
            color: white;
            padding: 8px 15px;
            text-decoration: none;
            border-radius: 4px;
            font-size: 14px;
        }
        .logout-btn:hover {
            background-color: #c82333;
        }
        .container {
            max-width: 900px;
            margin: 40px auto;
            padding: 20px;
        }
        .dashboard-cards {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
        }
        .card {
            background-color: white;
            padding: 25px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            text-align: center;
            transition: transform 0.2s;
        }
        .card:hover {
            transform: translateY(-5px);
        }
        .card h3 {
            color: #333;
            margin-bottom: 15px;
        }
        .card p {
            color: #666;
            font-size: 14px;
            margin-bottom: 20px;
        }
        .card a {
            display: inline-block;
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 4px;
            font-size: 14px;
        }
        .card a:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

    <div class="header">
        <h1>Admin Dashboard</h1>
        <a href="logout" class="logout-btn">Logout</a>
    </div>

    <div class="container">
        <div class="dashboard-cards">
            <!-- 1. View Users -->
            <div class="card">
                <h3>Manage Users</h3>
                <p>View all registered buyers and sellers in the system.</p>
                <a href="adminViewUsers.jsp">View Users</a>
            </div>

            <!-- 2. View Orders -->
            <div class="card">
                <h3>Manage Orders</h3>
                <p>Monitor all customer orders placed across the platform.</p>
                <a href="adminViewOrders.jsp">View Orders</a>
            </div>

            <!-- 3. Manage Products -->
            <div class="card">
                <h3>Manage Products</h3>
                <p>Review products and remove inappropriate items.</p>
                <a href="adminManageProducts.jsp">Manage Products</a>
            </div>
        </div>
    </div>

</body>
</html>