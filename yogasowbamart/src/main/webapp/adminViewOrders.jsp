<%@ page import="java.sql.*, com.yogasowbamart.dao.DBConnection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin - Manage Orders</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f7f6; margin: 0; padding: 20px; }
        .container { max-width: 1200px; margin: auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        h2 { color: #333; border-bottom: 2px solid #007bff; padding-bottom: 5px; margin-top: 20px; }
        table { width: 100%; border-collapse: collapse; margin-top: 15px; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; font-size: 14px; }
        th { background-color: #007bff; color: white; }
        tr:hover { background-color: #f1f1f1; }
        .back-btn { display: inline-block; margin-bottom: 20px; text-decoration: none; background: #6c757d; color: white; padding: 8px 15px; border-radius: 4px; }
        .back-btn:hover { background: #5a6268; }
        .status { font-weight: bold; color: #28a745; }
    </style>
</head>
<body>

<div class="container">
    <a href="adminDashboard.jsp" class="back-btn">&larr; Back to Dashboard</a>
    <h2>Manage Orders (Buyer Order Details)</h2>
    
    <table>
        <tr>
            <th>Order ID</th>
            <th>Buyer Name</th>
            <th>Product Name</th>
            <th>Quantity</th>
            <th>Price</th>
            <th>Total Amount</th>
            <th>Order Date</th>
            <th>Status</th>
        </tr>
        <%
            try {
                Connection con = DBConnection.getConnection();
                Statement stmt = con.createStatement();
                
                
                String query = "SELECT order_id, buyer_name, product_name, quantity, price, total_amount, order_date, status FROM orders";
                
                ResultSet rs = stmt.executeQuery(query);
                boolean hasOrders = false;
                
                while(rs.next()) {
                    hasOrders = true;
        %>
        <tr>
            <td><%= rs.getInt("order_id") %></td>
            <td><%= rs.getString("buyer_name") %></td>
            <td><%= rs.getString("product_name") %></td>
            <td><%= rs.getInt("quantity") %></td>
            <td>₹<%= rs.getDouble("price") %></td>
            <td>₹<%= rs.getDouble("total_amount") %></td>
            <td><%= rs.getString("order_date") %></td>
            <td><span class="status"><%= rs.getString("status") %></span></td>
        </tr>
        <% 
                }
                if (!hasOrders) {
                    out.println("<tr><td colspan='8' style='text-align:center; color:gray;'>No orders found in database.</td></tr>");
                }
                rs.close();
                stmt.close();
                con.close();
            } catch(Exception e) {
                out.println("<tr><td colspan='8' style='color:red;'>Error: " + e.getMessage() + "</td></tr>");
            }
        %>
    </table>
</div>

</body>
</html>