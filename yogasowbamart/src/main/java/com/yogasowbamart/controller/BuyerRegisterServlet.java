package com.yogasowbamart.controller;

import com.yogasowbamart.dao.BuyerDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/buyerRegister")
public class BuyerRegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        response.setContentType("text/html;charset=UTF-8");

        if (password != null && password.equals(confirmPassword)) {
            BuyerDAO buyerDAO = new BuyerDAO();
            boolean isRegistered = buyerDAO.registerBuyer(name, email, password);

            if (isRegistered) {
                response.getWriter().println("<h3 style='color:green; text-align:center;'>Registration Successful! <a href='buyerLogin.html'>Click here to Login</a></h3>");
            } else {
                response.getWriter().println("<h3 style='color:red; text-align:center;'>Registration Failed! Please check if table 'buyers' exists. <a href='buyerRegister.html'>Try Again</a></h3>");
            }
        } else {
            response.getWriter().println("<h3 style='color:red; text-align:center;'>Passwords do not match! <a href='buyerRegister.html'>Try Again</a></h3>");
        }
    }
}