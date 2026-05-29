<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Giriş Yap</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-5" style="max-width:450px;">
    <div class="card p-4 shadow-sm mt-5">
        <h4 class="mb-3 text-center">Hesabınıza Giriş Yapın</h4>
        <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
        <c:if test="${param.success == 1}"><div class="alert alert-success">Kayıt Başarılı! Giriş yapabilirsiniz.</div></c:if>
        
        <form action="login" method="post">
            <div class="mb-3">
                <label>E-posta Adresi</label>
                <input type="email" name="email" required class="form-control">
            </div>
            <div class="mb-3">
                <label>Şifre</label>
                <input type="password" name="password" required class="form-control">
            </div>
            <button class="btn btn-dark w-100">Giriş Yap</button>
        </form>
        <p class="mt-3 small text-center">Hesabınız yok mu? <a href="register">Kayıt Olun</a></p>
    </div>
</div>
</body>
</html>