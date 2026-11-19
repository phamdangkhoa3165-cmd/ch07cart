package org.example.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.model.business.Cart;
import org.example.model.business.LineItem;
import org.example.model.business.Product;

import java.io.IOException;

@WebServlet("/cart")
public class cartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "/index.html";
        String action = request.getParameter("action");
        if (action == null) {
            action = "cart";
        }

        if (action.equals("shop")) {
            url = "/index.html";
        } else if (action.equals("checkout")) {
            request.getSession().removeAttribute("cart");
            url = "/index.html";
        } else if (action.equals("cart") || action.equals("update")) {
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
            }

            String productCode = request.getParameter("productCode");
            if (productCode != null) {
                Product product = createProduct(productCode);
                if (product != null) {
                    int quantity = parseQuantity(request.getParameter("quantity"));
                    LineItem lineItem = new LineItem(product, quantity);

                    if (action.equals("update")) {
                        cart.updateItem(lineItem);
                    } else if (quantity > 0) {
                        cart.addItem(lineItem);
                    } else {
                        cart.removeItem(lineItem);
                    }
                }
            }

            session.setAttribute("cart", cart);
            url = "/cart.jsp";
        }

        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    private int parseQuantity(String quantityString) {
        if (quantityString == null) {
            return 1;
        }
        try {
            int quantity = Integer.parseInt(quantityString.trim());
            return quantity < 0 ? 1 : quantity;
        } catch (NumberFormatException exception) {
            return 1;
        }
    }

    private Product createProduct(String code) {
        switch (code) {
            case "8601":
                return new Product(code,
                        "86 (the band) - True Life Songs and Pictures", 14.95);
            case "pf01":
                return new Product(code, "Paddlefoot - The first CD", 12.95);
            case "pf02":
                return new Product(code, "Paddlefoot - The second CD", 12.95);
            case "jr01":
                return new Product(code, "Joe Rut - Genuine Wood Grained Finish", 12.95);
            default:
                return null;
        }
    }
}
