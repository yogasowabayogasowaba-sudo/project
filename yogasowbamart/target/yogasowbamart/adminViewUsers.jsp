<%@ page import="java.sql.*, com.yogasowbamart.dao.DBConnection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin - Manage Users</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f7f6; margin: 0; padding: 20px; }
        .container { max-width: 1000px; margin: auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        h2 { color: #333; border-bottom: 2px solid #007bff; padding-bottom: 5px; margin-top: 30px; }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; margin-bottom: 30px; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background-color: #007bff; color: white; }
        tr:hover { background-color: #f1f1f1; }
        .back-btn { display: inline-block; margin-bottom: 20px; text-decoration: none; background: #6c757d; color: white; padding: 8px 15px; border-radius: 4px; }
        .back-btn:hover { background: #5a6268; }
    </style>
</head>
<body>

<div class="container">
    <a href="adminDashboard.jsp" class="back-btn">&larr; Back to Dashboard</a>
    <h1>Manage Users (Buyers & Sellers)</h1>

    <!-- 1. Buyer Users Section -->
    <h2>Registered Buyer Users</h2>
    <table>
        <tr>
            <th>Buyer ID</th>
            <th>Name</th>
            <th>Email</th>
        </tr>
        <%
            try {
                Connection con = DBConnection.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rsBuyer = stmt.executeQuery("SELECT * FROM buyers");
                while(rsBuyer.next()) {
        %>
        <tr>
            <td><%= rsBuyer.getInt("id") %></td>
            <td><%= rsBuyer.getString("name") %></td>
            <td><%= rsBuyer.getString("email") %></td>
        </tr>
        <% 
                }
                rsBuyer.close();
        %>
    </table>

    <!-- 2. Seller Users Section -->
    <h2>Registered Seller Users</h2>
    <table>
        <tr>
            <th>Seller ID</th>
            <th>Name</th>
            <th>Email</th>
        </tr>
        <%
                ResultSet rsSeller = stmt.executeQuery("SELECT * FROM sellers");
                while(rsSeller.next()) {
        %>
        <tr>
            <td><%= rsSeller.getInt("id") %></td>
            <td><%= rsSeller.getString("name") %></td>
            <td><%= rsSeller.getString("email") %></td>
        </tr>
        <% 
                }
                rsSeller.close();
                stmt.close();
                con.close();
            } catch(Exception e) {
                out.println("<tr><td colspan='3' style='color:red;'>Error: " + e.getMessage() + "</td></tr>");
            }
        %>
    </table>
</div>

</body>
</html>