package com.yogasowbamart.dao;

import com.yogasowbamart.model.Order;
import com.yogasowbamart.dao.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    // 1. ஆர்டர் செய்யும் போது கார்ட்டில் இருந்து உண்மையான விலை, குவாண்டிட்டி எடுத்து ஆர்டர் டேபிளில் சேமிக்க
    public boolean placeOrder(String userEmail, String[] selectedItems) {
        if (selectedItems == null || selectedItems.length == 0) {
            return false;
        }

        boolean isSuccess = false;
        
        // யூசரின் பெயரை எடுக்க குவரி (உங்களுடைய டேபிள் பெயர் 'users' மற்றும் காலம் 'name' என假設 செய்கிறோம்)
        String getUserNameQuery = "SELECT name FROM users WHERE email = ?";
        String getCartItemQuery = "SELECT product_name, price, quantity FROM cart WHERE user_email = ? AND (product_name = ? OR product_name LIKE ?)";
        // buyer_name காலத்தை இன்செர்ட் குவரியில் சேர்த்துள்ளோம்
        String insertQuery = "INSERT INTO orders (user_email, buyer_name, product_name, price, quantity, total_amount, order_date, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String deleteCartQuery = "DELETE FROM cart WHERE user_email = ?"; 

        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            // 1. முதலில் யூசரின் பெயரை கண்டுபிடிக்கிறோம்
            String buyerName = "Guest";
            try (PreparedStatement pstmtUser = conn.prepareStatement(getUserNameQuery)) {
                pstmtUser.setString(1, userEmail);
                try (ResultSet rsUser = pstmtUser.executeQuery()) {
                    if (rsUser.next()) {
                        String name = rsUser.getString("name");
                        if (name != null && !name.trim().isEmpty()) {
                            buyerName = name;
                        }
                    }
                }
            } catch (SQLException e) {
                // பெயர் கிடைக்கவில்லை என்றால் 'Guest' என்றே இருக்கட்டும்
            }

            try (PreparedStatement pstmtGetCart = conn.prepareStatement(getCartItemQuery);
                 PreparedStatement pstmtInsert = conn.prepareStatement(insertQuery);
                 PreparedStatement pstmtDelete = conn.prepareStatement(deleteCartQuery)) {

                for (String item : selectedItems) {
                    double price = 0.0;
                    int quantity = 1;
                    String dbProductName = item;

                    // இன்டெக்ஸை நீக்குதல்
                    String actualProductName = item;
                    if (item.contains("_")) {
                        int lastIndex = item.lastIndexOf("_");
                        actualProductName = item.substring(0, lastIndex);
                    }

                    String keyword = "%" + actualProductName + "%";
                    if (actualProductName.toLowerCase().contains("kurti")) {
                        keyword = "%Kurti%";
                    } else if (actualProductName.toLowerCase().contains("jeans")) {
                        keyword = "%Jeans%";
                    }

                    pstmtGetCart.setString(1, userEmail);
                    pstmtGetCart.setString(2, actualProductName);
                    pstmtGetCart.setString(3, keyword);
                    
                    try (ResultSet rs = pstmtGetCart.executeQuery()) {
                        if (rs.next()) {
                            dbProductName = rs.getString("product_name");
                            price = rs.getDouble("price");
                            quantity = rs.getInt("quantity");
                        }
                    }

                    if (price == 0.0) {
                        String lowerItem = actualProductName.toLowerCase();
                        if (lowerItem.contains("kurti")) {
                            price = 599.0;
                        } else if (lowerItem.contains("jeans")) {
                            price = 999.0;
                        } else if (lowerItem.contains("yoga mat")) {
                            price = 799.0;
                        }
                    }
                    double totalAmount = price * quantity;

                    pstmtInsert.setString(1, userEmail);
                    pstmtInsert.setString(2, buyerName); // வாடிக்கையாளரின் உண்மையான பெயர் இங்கே செல்கிறது
                    pstmtInsert.setString(3, dbProductName); 
                    pstmtInsert.setDouble(4, price);
                    pstmtInsert.setInt(5, quantity);
                    pstmtInsert.setDouble(6, totalAmount);
                    pstmtInsert.setString(7, currentDate);
                    pstmtInsert.setString(8, "Placed");
                    pstmtInsert.addBatch();
                }
                
                pstmtInsert.executeBatch();

                pstmtDelete.setString(1, userEmail);
                pstmtDelete.executeUpdate();

                conn.commit();
                isSuccess = true;

            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                isSuccess = false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isSuccess;
    }

    // 2. செல்லர் பக்கத்திற்கு அனைத்து ஆர்டர்களையும் எடுக்க (Get All Orders)
    public List<Order> getAllOrders() {
        List<Order> ordersList = new ArrayList<>();
        // buyer_name காலத்தை SELECT குவரியில் சேர்த்துள்ளோம்
        String query = "SELECT order_id, user_email, buyer_name, product_name, price, quantity, total_amount, order_date, status FROM orders ORDER BY order_id DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setCustomerEmail(rs.getString("user_email"));
                order.setBuyerName(rs.getString("buyer_name")); // பெயர் செட் செய்யப்படுகிறது
                order.setProductName(rs.getString("product_name"));
                order.setPrice(rs.getDouble("price"));
                order.setQuantity(rs.getInt("quantity"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setOrderDate(rs.getString("order_date"));
                order.setStatus(rs.getString("status"));
                
                ordersList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordersList;
    }

    // 3. குறிப்பிட்ட யூசரின் ஆர்டர்களை எடுக்க (Get Orders By User)
    public List<Order> getOrdersByUser(String userEmail) {
        List<Order> ordersList = new ArrayList<>();
        String query = "SELECT order_id, user_email, buyer_name, product_name, price, quantity, total_amount, order_date, status WHERE user_email = ? ORDER BY order_id DESC";
        // மேலே உள்ள குவரியில் 'FROM orders' விடுபட்டுவிடாமல் இருக்க கவனிக்கவும் (கீழே உள்ளதை பயன்படுத்தவும்):
        String safeQuery = "SELECT order_id, user_email, buyer_name, product_name, price, quantity, total_amount, order_date, status FROM orders WHERE user_email = ? ORDER BY order_id DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(safeQuery)) {
            
            pstmt.setString(1, userEmail);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setOrderId(rs.getInt("order_id"));
                    order.setCustomerEmail(rs.getString("user_email"));
                    order.setBuyerName(rs.getString("buyer_name"));
                    order.setProductName(rs.getString("product_name"));
                    order.setPrice(rs.getDouble("price"));
                    order.setQuantity(rs.getInt("quantity"));
                    order.setTotalAmount(rs.getDouble("total_amount"));
                    order.setOrderDate(rs.getString("order_date"));
                    order.setStatus(rs.getString("status"));
                    
                    ordersList.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordersList;
    }
}