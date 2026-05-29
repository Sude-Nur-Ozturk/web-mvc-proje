<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>E-Ticaret Portalı</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">Portal Mağaza</a>
        <div class="navbar-nav ms-auto">
            <a class="nav-link text-white" href="cart">Sepetim</a>
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <a class="nav-link text-white" href="my-orders">Siparişlerim</a>
                    <span class="nav-link text-secondary">Sn. ${sessionScope.user.fullName}</span>
                    <a class="nav-link btn btn-danger btn-sm text-white px-2 ms-2" href="logout">Çıkış</a>
                </c:when>
                <c:otherwise>
                    <a class="nav-link text-white" href="login">Giriş Yap</a>
                    <a class="nav-link text-white" href="register">Kayıt Ol</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>

<div class="container">
    <div class="row">
        <div class="col-md-3">
            <div class="list-group">
                <a href="${pageContext.request.contextPath}/" class="list-group-item list-group-item-action">Tüm Kategoriler</a>
                <c:forEach var="cat" items="${categories}">
                    <a href="?category=${cat.id}" class="list-group-item list-group-item-action">${cat.name}</a>
                </c:forEach>
            </div>
        </div>
        <div class="col-md-9">
            <div class="row">
                <c:forEach var="p" items="${products}">
                    <div class="col-md-4 mb-4">
                        <div class="card h-100 shadow-sm">
                            <img src="${p.imageUrl}" class="card-img-top" alt="Görsel bulunamadı" style="height: 180px; object-fit: cover;">
                            <div class="card-body d-flex flex-column">
                                <h5 class="card-title">${p.name}</h5>
                                <p class="text-muted small">${p.description}</p>
                                <p class="text-primary fw-bold mt-auto">
                                    <fmt:formatNumber value="${p.price}" type="currency" currencySymbol="₺"/>
                                </p>
                                <p class="small text-secondary">Stok Durumu: ${p.stock > 0 ? p.stock : 'Stokta Yok'}</p>
                                <div class="mt-2">
                                    <a href="product?id=${p.id}" class="btn btn-sm btn-outline-secondary">Detay</a>
                                    <c:choose>
                                        <c:when test="${p.stock > 0}">
                                            <a href="cart?action=add&id=${p.id}" class="btn btn-sm btn-success">Sepete Ekle</a>
                                        </c:when>
                                        <c:otherwise>
                                            <button class="btn btn-sm btn-secondary" disabled>Stokta Yok</button>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>
</html>