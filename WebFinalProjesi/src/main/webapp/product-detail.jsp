<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>${product.name} - Detay</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-5">
    <div class="card p-4 shadow-sm">
        <div class="row">
            <div class="col-md-4"><img src="${product.imageUrl}" class="img-fluid rounded" alt="#"></div>
            <div class="col-md-8">
                <h2>${product.name}</h2>
                <p class="badge bg-info text-dark">Kategori: ${product.categoryName}</p>
                <hr>
                <p>${product.description}</p>
                <h4 class="text-success"><fmt:formatNumber value="${product.price}" type="currency" currencySymbol="₺"/></h4>
                <p class="text-muted">Mevcut Stok: ${product.stock}</p>
                
                <c:choose>
                    <c:when test="${product.stock > 0}">
                        <a href="cart?action=add&id=${product.id}" class="btn btn-lg btn-success">Sepete Ekle</a>
                    </c:when>
                    <c:otherwise>
                        <div class="alert alert-danger d-inline-block">Bu Ürün Stokta Yok!</div>
                    </c:otherwise>
                </c:choose>
                <br><br>
                <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">Ana Sayfaya Dön</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>