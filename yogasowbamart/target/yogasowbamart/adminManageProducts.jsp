<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin - Manage Products | Yoga Sowba Mart</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f6f9; margin: 0; padding: 20px; }
        .container { max-width: 1000px; margin: auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        h2 { color: #333; text-align: center; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 12px; border: 1px solid #ddd; text-align: center; }
        th { background-color: #ffc107; color: #333; }
        tr:nth-child(even) { background-color: #f9f9f9; }
        .back-btn { display: inline-block; margin-bottom: 15px; padding: 8px 15px; background-color: #6c757d; color: white; text-decoration: none; border-radius: 4px; }
        .back-btn:hover { background-color: #5a6268; }
        .action-btn { padding: 5px 10px; margin: 2px; text-decoration: none; border-radius: 3px; color: white; }
        .edit-btn { background-color: #17a2b8; }
        .delete-btn { background-color: #dc3545; }
    </style>
</head>
<body>

    <div class="container">
        <a href="adminDashboard.jsp" class="back-btn">&larr; Back to Dashboard</a>
        <h2>Manage Products</h2>
        
        <table>
            <thead>
                <tr>
                    <th>Product ID</th>
                    <th>Product Name</th>
                    <th>Price</th>
                    <th>Stock</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <!-- இங்கு டேட்டாபேஸில் இருந்து ப்ராடக்ட் விவரங்கள் டைனமிக் முறையில் லோட் செய்யப்படும் -->
                <tr>
                    <td>201</td>
                    <td>Yoga Mat</td>
                    <td>₹799</td>
                    <td>50</td>
                    <td>
                        <a href="#" class="action-btn edit-btn">Edit</a>
                        <a href="#" class="action-btn delete-btn">Delete</a>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>

</body>
</html>