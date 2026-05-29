<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Siparişlerim</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h3>Geçmiş Siparişleriniz</h3>
    <table class="table table-striped table-bordered bg-white shadow-sm mt-3">
        <thead class="table-dark">
            <tr>
                <th>Sipariş No</th><th>Tarih</th><th>Toplam Tutar</th><th>Durum</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="o" items="${orders}">
                <tr>
                    <td># ${o.id}</td>
                    <td><fmt:formatDate value="${o.orderDate}" pattern="dd-MM-yyyy HH:mm"/></td>
                    <td><fmt:formatNumber value="${o.totalAmount}" type="currency" currencySymbol="₺"/></td>
                    <td>
                        <span class="badge ${o.status == 'Beklemede' ? 'bg-warning text-dark' : 'bg-success'}">${o.status}</span>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">Mağazaya Dön</a>
</div>
</body>
</html>