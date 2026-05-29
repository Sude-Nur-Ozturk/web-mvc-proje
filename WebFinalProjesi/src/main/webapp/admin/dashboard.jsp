<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Yönetim Paneli</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
<div class="d-flex">
    <div class="bg-dark text-white p-3 min-vh-100" style="width: 240px;">
        <h5>Admin Menü</h5><hr>
        <ul class="nav flex-column">
            <li class="nav-item"><a class="nav-link text-white fw-bold" href="dashboard">Dashboard</a></li>
            <li class="nav-item"><a class="nav-link text-white" href="categories">Kategori Yönetimi</a></li>
            <li class="nav-item"><a class="nav-link text-white" href="products">Ürün Yönetimi</a></li>
            <li class="nav-item"><a class="nav-link text-white" href="orders">Sipariş Yönetimi</a></li>
            <li class="nav-item"><a class="nav-link text-white" href="users">Kullanıcı Listesi</a></li>
            <li class="nav-item"><a class="nav-link text-danger mt-4" href="../logout">Çıkış Yap</a></li>
        </ul>
    </div>
    <div class="p-4 w-100 bg-light">
        <h2>Yönetim Paneli Özet Bilgiler</h2><hr>
        <div class="row">
            <div class="col-md-3"><div class="card bg-primary text-white p-3 mb-3"><h5>Toplam Ürün</h5><h3>${pCount}</h3></div></div>
            <div class="col-md-3"><div class="card bg-success text-white p-3 mb-3"><h5>Toplam Kategori</h5><h3>${cCount}</h3></div></div>
            <div class="col-md-3"><div class="card bg-info text-white p-3 mb-3"><h5>Toplam Müşteri</h5><h3>${uCount}</h3></div></div>
            <div class="col-md-3"><div class="card bg-danger text-white p-3 mb-3"><h5>Bekleyen Sipariş</h5><h3>${pendingCount} / ${oCount}</h3></div></div>
        </div>
    </div>
</div>
</body>
</html>