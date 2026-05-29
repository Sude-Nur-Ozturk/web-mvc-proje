<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Kategori Formu</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-5" style="max-width: 600px;">
    <div class="card p-4 shadow-sm">
        <h4>${empty category ? 'Yeni Kategori Ekle' : 'Kategoriyi Güncelle'}</h4><hr>
        <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
        
        <form action="category-form" method="post">
            <input type="hidden" name="id" value="${category.id}">
            
            <div class="mb-3">
                <label>Kategori Adı *</label>
                <input type="text" name="name" value="${category.name}" class="form-control" required>
            </div>
            <div class="mb-3">
                <label>Açıklama</label>
                <textarea name="description" class="form-control" rows="3">${category.description}</textarea>
            </div>
            <div class="form-check mb-3">
                <input type="checkbox" name="isActive" class="form-check-input" id="act" ${category == null || category.is_active() ? 'checked' : ''}>
                <label class="form-check-label" for="act">Aktif (Sitede Görünsün)</label>
            </div>
            <button class="btn btn-success">Kaydet</button>
            <a href="categories" class="btn btn-secondary">İptal</a>
        </form>
    </div>
</div>
</body>
</html>