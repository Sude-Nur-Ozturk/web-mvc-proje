<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sipariş Detay Kontrolü</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-5" style="max-width: 800px;">
    <div class="card p-4 shadow-sm bg-white">
        <h4>Sipariş Detayı (# ${order.id})</h4><hr>
        <p><strong>Müşteri:</strong> ${order.customerName}</p>
        <p><strong>Tarih:</strong> <fmt:formatDate value="${order.orderDate}" pattern="dd-MM-yyyy HH:mm"/></p>
        <p><strong>Güncel Durum:</strong> <span class="badge bg-secondary">${order.status}</span></p>
        
        <form action="order-detail" method="post" class="row g-3 my-3 align-items-center">
            <input type="hidden" name="orderId" value="${order.id}">
            <div class="col-auto"><label class="col-form-label">Durumu Güncelle:</label></div>
            <div class="col-auto">
                <select name="status" class="form-select form-select-sm">
                    <option value="Beklemede" ${order.status == 'Beklemede' ? 'selected' : ''}>Beklemede</option>
                    <option value="Hazırlanıyor" ${order.status == 'Hazırlanıyor' ? 'selected' : ''}>Hazırlanıyor</option>
                    <option value="Kargoya Verildi" ${order.status == 'Kargoya Verildi' ? 'selected' : ''}>Kargoya Verildi</option>
                    <option value="Tamamlandı" ${order.status == 'Tamamlandı' ? 'selected' : ''}>Tamamlandı</option>
                    <option value="İptal Edildi" ${order.status == 'İptal Edildi' ? 'selected' : ''}>İptal Edildi</option>
                </select>
            </div>
            <div class="col-auto"><button class="btn btn-sm btn-primary">Kaydet</button></div>
        </form>

        <h5 class="mt-4">Ürünler</h5>
        <table class="table table-bordered align-middle">
            <thead><tr><th>Ürün Adı</th><th>Birim Fiyat</th><th>Miktar</th><th>Toplam</th></tr></thead>
            <tbody>
                <c:forEach var="item" items="${order.items}">
                    <tr>
                        <td>${item.productName}</td>
                        <td><fmt:formatNumber value="${item.unitPrice}" type="currency" currencySymbol="₺"/></td>
                        <td>${item.quantity}</td>
                        <td><fmt:formatNumber value="${item.subtotal}" type="currency" currencySymbol="₺"/></td>
                    </tr>
                </c:forEach>
                <tr class="table-light fw-bold"><td colspan="3" class="text-end">Genel Sipariş Toplamı:</td><td><fmt:formatNumber value="${order.totalAmount}" type="currency" currencySymbol="₺"/></td></tr>
            </tbody>
        </table>
        <a href="orders" class="btn btn-secondary btn-sm">Sipariş Listesine Dön</a>
    </div>
</div>
</body>
</html>