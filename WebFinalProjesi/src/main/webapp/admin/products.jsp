<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ürün Yönetimi</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>Ürün Yönetimi</h3>
        <div>
            <a href="product-form" class="btn btn-success">Yeni Ürün Ekle</a>
            <a href="dashboard" class="btn btn-secondary">Panele Dön</a>
        </div>
    </div>
    <div class="card shadow-sm p-3">
        <table class="table table-hover align-middle">
            <thead class="table-dark">
                <tr>
                    <th>ID</th><th>Görsel</th><th>Ürün Adı</th><th>Kategori</th><th>Fiyat</th><th>Stok</th><th>Durum</th><th>İşlemler</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="p" items="${products}">
                    <tr>
                        <td>${p.id}</td>
                        <td><img src="${p.imageUrl}" alt="${p.name}" style="width: 50px; height: 50px; object-fit: cover; border-radius: 5px;"></td>
                        <td>${p.name}</td>
                        <td>${p.categoryName}</td>
                        <td><fmt:formatNumber value="${p.price}" type="currency" currencySymbol="₺"/></td>
                        <td>${p.stock}</td>
                        <td>
                            <span class="badge ${p.is_active() ? 'bg-success' : 'bg-danger'}">${p.is_active() ? 'Aktif' : 'Pasif'}</span>
                        </td>
                        <td>
                            <a href="product-form?id=${p.id}" class="btn btn-sm btn-primary">Düzenle</a>
                            <a href="product-delete?id=${p.id}" class="btn btn-sm btn-danger" onclick="return confirm('Bu ürünü silmek istediğinize emin misiniz?');">Sil</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>