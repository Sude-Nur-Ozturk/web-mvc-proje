<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Kullanıcı Listesi</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>Sistem Kullanıcıları</h3>
        <a href="dashboard" class="btn btn-secondary">Panele Dön</a>
    </div>
    <div class="card shadow-sm p-3">
        <table class="table table-striped align-middle">
            <thead class="table-dark">
                <tr>
                    <th>ID</th><th>Ad Soyad</th><th>E-posta</th><th>Telefon</th><th>Rol</th><th>Kayıt Tarihi</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="u" items="${users}">
                    <tr>
                        <td>${u.id}</td>
                        <td>${u.fullName}</td>
                        <td>${u.email}</td>
                        <td>${empty u.phone ? '-' : u.phone}</td>
                        <td>
                            <span class="badge ${u.role == 'admin' ? 'bg-danger' : 'bg-primary'}">${u.role}</span>
                        </td>
                        <td><fmt:formatDate value="${u.createdAt}" pattern="dd-MM-yyyy HH:mm"/></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>