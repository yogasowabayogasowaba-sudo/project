package com.yogasowbamart.controller;

import com.yogasowbamart.dao.ProductDAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String keyword = request.getParameter("keyword");
        String category = request.getParameter("category");

        ProductDAO productDAO = new ProductDAO();
        List<String[]> products = productDAO.searchProducts(keyword, category);

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<html><body>");
        out.println("<h2>YogasowbaMart - Product Search</h2>");
        
        out.println("<form action='products' method='GET'>");
        out.println("Search: <input type='text' name='keyword' value='" + (keyword != null ? keyword : "") + "'> ");
        out.println("Category: <select name='category'>");
        out.println("<option value='All'>All</option>");
        out.println("<option value='Electronics' " + ("Electronics".equals(category) ? "selected" : "") + ">Electronics</option>");
        out.println("<option value='Footwear' " + ("Footwear".equals(category) ? "selected" : "") + ">Footwear</option>");
        out.println("</select> ");
        out.println("<input type='submit' value='Filter'>");
        out.println("</form><hr>");

        // Action காலம் நீக்கப்பட்டு, வெறும் விவரங்கள் மட்டும் இருக்கும் டேபிள்
        out.println("<table border='1'><tr><th>ID</th><th>Name</th><th>Description</th><th>Price</th><th>Category</th></tr>");
        
        if (products.isEmpty()) {
            out.println("<tr><td colspan='5'>No products found!</td></tr>");
        } else {
            for (String[] p : products) {
                out.println("<tr>");
                out.println("<td>" + p[0] + "</td>");
                out.println("<td>" + p[1] + "</td>");
                out.println("<td>" + p[2] + "</td>");
                out.println("<td>Rs. " + p[3] + "</td>");
                out.println("<td>" + p[4] + "</td>");
                out.println("</tr>");
            }
        }
        out.println("</table>");
        out.println("</body></html>");
    }
}