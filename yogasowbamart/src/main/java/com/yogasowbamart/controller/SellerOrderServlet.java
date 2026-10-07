package com.yogasowbamart.controller;
import com.yogasowbamart.dao.OrderDAO;
import com.yogasowbamart.model.Order;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/sellerOrders")
public class SellerOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        OrderDAO orderDAO = new OrderDAO();
        List<Order> orderList = orderDAO.getAllOrders();
    
        request.setAttribute("orderList", orderList);

       request.getRequestDispatcher("sellerOrder.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}