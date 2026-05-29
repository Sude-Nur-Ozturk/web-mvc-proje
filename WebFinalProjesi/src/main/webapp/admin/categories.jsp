<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Kategori Yönetimi</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>Kategori Yönetimi</h3>
        <div>
            <a href="category-form" class="btn btn-success">Yeni Kategori Ekle</a>
            <a href="dashboard" class="btn btn-secondary">Panele Dön</a>
        </div>
    </div>
    <div class="card shadow-sm p-3">
        <table class="table table-hover align-middle">
            <thead class="table-dark">
                <tr>
                    <th>ID</th><th>Kategori Adı</th><th>Açıklama</th><th>Durum</th><th>İşlemler</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="c" items="${categories}">
                    <tr>
                        <td>${c.id}</td>
                        <td>${c.name}</td>
                        <td>${c.description}</td>
                        <td>
                            <span class="badge ${c.is_active() ? 'bg-success' : 'bg-danger'}">${c.is_active() ? 'Aktif' : 'Pasif'}</span>
                        </td>
                        <td>
                            <a href="category-form?id=${c.id}" class="btn btn-sm btn-primary">Düzenle</a>
                            <a href="category-delete?id=${c.id}" class="btn btn-sm btn-danger" onclick="return confirm('Bu işlemi onaylıyor musunuz? (Bağlı ürün varsa kategori pasife çekilecektir)');">Sil / Pasif Yap</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>