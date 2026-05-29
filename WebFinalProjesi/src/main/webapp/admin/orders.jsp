<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sipariş Yönetimi</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3>Tüm Siparişler</h3>
        <a href="dashboard" class="btn btn-secondary">Panele Dön</a>
    </div>
    <div class="card shadow-sm p-3">
        <table class="table table-hover align-middle">
            <thead class="table-dark">
                <tr>
                    <th>Sipariş No</th><th>Müşteri</th><th>Tarih</th><th>Tutar</th><th>Durum</th><th>İşlem</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="o" items="${orders}">
                    <tr>
                        <td># ${o.id}</td>
                        <td>${o.customerName}</td>
                        <td><fmt:formatDate value="${o.orderDate}" pattern="dd-MM-yyyy HH:mm"/></td>
                        <td><fmt:formatNumber value="${o.totalAmount}" type="currency" currencySymbol="₺"/></td>
                        <td>
                            <span class="badge ${o.status == 'Beklemede' ? 'bg-warning text-dark' : 'bg-success'}">${o.status}</span>
                        </td>
                        <td>
                            <a href="order-detail?id=${o.id}" class="btn btn-sm btn-info text-white">Detay/Güncelle</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>