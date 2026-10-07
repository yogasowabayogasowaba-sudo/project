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
        
        // 1. செஷனில் இருந்து லாகின் செய்துள்ள யூசரின் ஈமெயிலைப் பெறுதல்
        String userEmail = (String) session.getAttribute("userEmail");
        if (userEmail == null) {
            userEmail = (String) session.getAttribute("email");
        }

        // 2. செக் அவுட் ஃபார்மில் இருந்து வரும் தேர்ந்தெடுக்கப்பட்ட பொருட்களைப் பெறுதல்
        String[] selectedItems = request.getParameterValues("selectedProducts");
        
        if (selectedItems == null || selectedItems.length == 0) {
            selectedItems = (String[]) session.getAttribute("cartItems");
        }

        // 3. யூசர் அல்லது பொருட்கள் இல்லாவிட்டால் எரர் பக்கத்திற்கு அனுப்புதல்
        if (userEmail == null || selectedItems == null || selectedItems.length == 0) {
            response.sendRedirect("orderFailed.jsp");
            return;
        }

        // 4. ஆர்டரை டேட்டாபேஸில் பதிவு செய்தல்
        OrderDAO orderDAO = new OrderDAO();
        boolean isPlaced = orderDAO.placeOrder(userEmail, selectedItems);

        if (isPlaced) {
            // **மிக முக்கியம்:** ஆர்டர் வெற்றிகரமாக முடிந்ததும் கார்ட்டைக் காலி செய்தல் (Clear Cart)
            session.removeAttribute("cartItems");
            session.removeAttribute("selectedProducts");
            session.removeAttribute("cart");
            
            // கார்ட் தொடர்பான வேறு ஏதேனும் செஷன் பெயர்கள் இருந்தால் அவற்றையும் நீக்குதல்
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