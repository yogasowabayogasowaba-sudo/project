package com.yogasowbamart.controller;

import com.yogasowbamart.dao.BuyerDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/buyerLogin")
public class BuyerLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        response.setContentType("text/html;charset=UTF-8");

        BuyerDAO buyerDAO = new BuyerDAO();
        boolean isValid = buyerDAO.validateBuyer(email, password);

        if (isValid) {
            HttpSession session = request.getSession();
            session.setAttribute("userEmail", email);
            response.sendRedirect("home.html");
        } else {
            response.getWriter().println("<h3 style='color:red; text-align:center;'>Invalid Email or Password! <a href='buyerLogin.html'>Try Again</a></h3>");
        }
    }
}