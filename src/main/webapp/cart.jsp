<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Your cart</title>
    <link rel="stylesheet" href="main.css" type="text/css">
</head>
<body>
    <h1>Your cart</h1>

    <table class="cart-table">
        <thead>
        <tr>
            <th>Quantity</th>
            <th>Description</th>
            <th>Price</th>
            <th>Amount</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${cart.items}">
            <tr>
                <td>
                    <form class="qty-form" action="cart" method="post">
                        <input type="hidden" name="action" value="cart">
                        <input type="hidden" name="productCode" value="<c:out value='${item.product.code}'/>">
                        <input type="number" class="qty-input" name="quantity" min="0" value="<c:out value='${item.quantity}'/>">
                        <input type="submit" value="Update">
                    </form>
                </td>
                <td><c:out value="${item.product.description}"/></td>
                <td>${item.product.priceCurrencyFormat}</td>
                <td>${item.totalCurrencyFormat}</td>
                <td>
                    <form action="cart" method="post">
                        <input type="hidden" name="action" value="cart">
                        <input type="hidden" name="productCode" value="<c:out value='${item.product.code}'/>">
                        <input type="hidden" name="quantity" value="0">
                        <input type="submit" value="Remove Item">
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <p>To change the quantity, enter the new quantity and click the Update button.</p>

    <form action="cart" method="post">
        <input type="hidden" name="action" value="shop">
        <input type="submit" value="Continue Shopping">
    </form>
    <form action="cart" method="post">
        <input type="hidden" name="action" value="checkout">
        <input type="submit" value="Checkout">
    </form>
</body>
</html>