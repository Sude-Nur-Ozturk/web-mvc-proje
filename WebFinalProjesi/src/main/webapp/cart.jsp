<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sepetim</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h3>Alışveriş Sepetiniz</h3>
    <c:if test="${not empty msg}"><div class="alert alert-success">${msg}</div></c:if>
    <c:if test="${param.error == 1}"><div class="alert alert-danger">Sipariş tamamlanırken bir hata oluştu!</div></c:if>

    <c:choose>
        <c:when test="${empty sessionScope.cart}">
            <div class="alert alert-warning">Sepetiniz boş. <a href="${pageContext.request.contextPath}/">Hemen Alışverişe Başlayın!</a></div>
        </c:when>
        <c:otherwise>
            <table class="table table-bordered bg-white shadow-sm align-middle">
                <thead class="table-dark">
                    <tr>
                        <th>Ürün Adı</th><th>Birim Fiyat</th><th>Adet</th><th>Ara Toplam</th><th>İşlem</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${sessionScope.cart}">
                        <tr>
                            <td>${item.product.name}</td>
                            <td><fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="₺"/></td>
                            <td>
                                <form action="cart" method="post" class="d-flex">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="id" value="${item.product.id}">
                                    <input type="number" name="quantity" value="${item.getQuantity()}" min="1" max="${item.product.stock}" class="form-control form-control-sm me-2" style="width:70px;">
                                    <button class="btn btn-sm btn-primary">Güncelle</button>
                                </form>
                            </td>
                            <td><fmt:formatNumber value="${item.subtotal}" type="currency" currencySymbol="₺"/></td>
                            <td><a href="cart?action=remove&id=${item.product.id}" class="btn btn-sm btn-danger">Çıkar</a></td>
                        </tr>
                    </c:forEach>
                    <tr class="table-secondary fw-bold">
                        <td colspan="3" class="text-end">Genel Toplam:</td>
                        <td colspan="2"><fmt:formatNumber value="${sessionScope.cartTotal}" type="currency" currencySymbol="₺"/></td>
                    </tr>
                </tbody>
            </table>
            <div class="d-flex justify-content-between">
                <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">Alışverişe Devam Et</a>
                <form action="checkout" method="post">
                    <button class="btn btn-lg btn-success">Siparişi Tamamla (Satın Al)</button>
                </form>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>