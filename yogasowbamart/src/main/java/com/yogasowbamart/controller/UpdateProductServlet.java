package com.yogasowbamart.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.yogasowbamart.dao.ProductDAO;

@WebServlet("/updateProduct")
public class UpdateProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String idStr = request.getParameter("id");
            
            // ஐடி சரியாக வருகிறதா என்று பார்க்க
            int id = Integer.parseInt(idStr);
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            double price = Double.parseDouble(request.getParameter("price"));
            int stock = Integer.parseInt(request.getParameter("stock"));
            String category = request.getParameter("category");

            ProductDAO productDAO = new ProductDAO();
            boolean isUpdated = productDAO.updateProduct(id, name, description, price, stock, category);

            if (isUpdated) {
                // நேராக viewProducts.jsp-க்குப் பதிலாக சர்வெலெட்டுக்கு ரீடைரக்ட் செய்யப்படுகிறது
                response.sendRedirect("viewProducts");
            } else {
                response.getWriter().println("Product update failed! ID received: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}