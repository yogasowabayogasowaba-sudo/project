package com.yogasowbamart.controller;

import com.yogasowbamart.dao.OrderDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

@WebServlet("/OrderSuccessServlet")
public class OrderSuccessServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
      
        String userEmail = (String) session.getAttribute("userEmail");
        if (userEmail == null) {
            userEmail = (String) session.getAttribute("email");
        }

       
        String[] selectedItems = request.getParameterValues("selectedProducts");
        
        if (selectedItems == null || selectedItems.length == 0) {
            selectedItems = (String[]) session.getAttribute("cartItems");
        }

        
        if (userEmail == null || selectedItems == null || selectedItems.length == 0) {
            response.sendRedirect("orderFailed.jsp");
            return;
        }

       
        OrderDAO orderDAO = new OrderDAO();
        boolean isPlaced = orderDAO.placeOrder(userEmail, selectedItems);

        if (isPlaced) {
           
            session.removeAttribute("cartItems");
            session.removeAttribute("selectedProducts");
            session.removeAttribute("cart");
            
            
            Enumeration<String> attrs = session.getAttributeNames();
            while (attrs.hasMoreElements()) {
                String attr = attrs.nextElement();
                if (attr.toLowerCase().contains("cart")) {
                    session.removeAttribute(attr);
                }
            }

            response.sendRedirect("orderSuccess.jsp");
        } else {
            response.sendRedirect("orderFailed.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}