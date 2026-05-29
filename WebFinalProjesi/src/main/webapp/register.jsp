<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Kayıt Ol</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-4" style="max-width:500px;">
    <div class="card p-4 shadow-sm">
        <h4 class="mb-3 text-center">Yeni Hesap Oluştur</h4>
        <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
        
        <form action="register" method="post">
            <div class="mb-3"><label>Ad Soyad *</label><input type="text" name="fullName" required class="form-control"></div>
            <div class="mb-3"><label>E-posta *</label><input type="email" name="email" required class="form-control"></div>
            <div class="mb-3"><label>Şifre *</label><input type="password" name="password" required class="form-control"></div>
            <div class="mb-3"><label>Telefon</label><input type="text" name="phone" class="form-control"></div>
            <div class="mb-3"><label>Adres</label><textarea name="address" class="form-control" rows="2"></textarea></div>
            <button class="btn btn-primary w-100">Kayıt Ol</button>
        </form>
    </div>
</div>
</body>
</html>