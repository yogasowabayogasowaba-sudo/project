package com.yogasowbamart.model;

public class Order {
    private int orderId;
    private String customerEmail;
    private String buyerName; // புதிய buyerName ஃபீல்ட்
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

    // buyerName க்கான Getters மற்றும் Setters (அட்மின் பக்கத்தில் பெயர் காட்ட)
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