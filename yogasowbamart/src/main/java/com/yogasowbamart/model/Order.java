package com.yogasowbamart.model;

public class Order {
    private int orderId;
    private int productId; // புதிய productId ஃபீல்ட் சேர்க்கப்பட்டுள்ளது
    private String customerEmail;
    private String buyerName; 
    private String productName;
    private double price; 
    private int quantity;
    private double totalAmount;
    private String status;
    private String orderDate;

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() { // ProductId க்கான Getter
        return productId;
    }

    public void setProductId(int productId) { // ProductId க்கான Setter
        this.productId = productId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    // sellerOrder.jsp க்காக கூடுதல் மேப்பிங் (UserEmail)
    public String getUserEmail() {
        return customerEmail;
    }

    public void setUserEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}