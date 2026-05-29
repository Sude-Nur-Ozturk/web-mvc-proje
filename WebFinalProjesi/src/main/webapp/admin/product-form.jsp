<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Ürün Formu</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-5" style="max-width: 600px;">
    <div class="card p-4 shadow-sm">
        <h4>${empty product ? 'Yeni Ürün Ekle' : 'Ürünü Güncelle'}</h4><hr>
        <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
        
        <form action="product-form" method="post">
            <input type="hidden" name="id" value="${product.id}">
            
            <div class="mb-3">
                <label>Ürün Adı *</label>
                <input type="text" name="name" value="${product.name}" class="form-control" required>
            </div>
            <div class="mb-3">
                <label>Kategori Seçin *</label>
                <select name="categoryId" class="form-select" required>
                    <c:forEach var="c" items="${categories}">
                        <option value="${c.id}" ${c.id == product.categoryId ? 'selected' : ''}>${c.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-3">
                <label>Fiyat (TL) *</label>
                <input type="number" step="0.01" name="price" value="${product.price}" class="form-control" required>
            </div>
            <div class="mb-3">
                <label>Stok Miktarı *</label>
                <input type="number" name="stock" value="${product.stock}" class="form-control" required>
            </div>
            <div class="mb-3">
                <label>Görsel Yolu (URL)</label>
                <input type="text" name="imageUrl" value="${product.imageUrl}" class="form-control">
            </div>
            <div class="mb-3">
                <label>Açıklama</label>
                <textarea name="description" class="form-control" rows="3">${product.description}</textarea>
            </div>
            <div class="form-check mb-3">
                <input type="checkbox" name="isActive" class="form-check-input" id="act" ${product == null || product.is_active() ? 'checked' : ''}>
                <label class="form-check-label" for="act">Aktif (Sitede Görünsün)</label>
            </div>
            <button class="btn btn-success">Kaydet</button>
            <a href="products" class="btn btn-secondary">İptal</a>
        </form>
    </div>
</div>
</body>
</html>