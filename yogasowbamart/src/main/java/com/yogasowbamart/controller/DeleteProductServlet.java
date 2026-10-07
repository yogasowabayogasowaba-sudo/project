package com.yogasowbamart.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.yogasowbamart.dao.ProductDAO;

@WebServlet("/deleteProduct")
public class DeleteProductServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("id"));

            ProductDAO productDAO = new ProductDAO();
            boolean isDeleted = productDAO.deleteProduct(productId);

            if (isDeleted) {
                response.sendRedirect("viewProducts.jsp"); 
            } else {
                response.getWriter().println("Product deletion failed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}