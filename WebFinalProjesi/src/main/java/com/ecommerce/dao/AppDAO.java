package com.ecommerce.dao;

import com.ecommerce.model.*;
import com.ecommerce.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppDAO {

    // --- USER İŞLEMLERİ ---
    public User login(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setId(rs.getInt("id"));
                    u.setFullName(rs.getString("full_name"));
                    u.setEmail(rs.getString("email"));
                    u.setRole(rs.getString("role"));
                    u.setAddress(rs.getString("address"));
                    return u;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean register(User u) {
        String checkSql = "SELECT id FROM users WHERE email = ?";
        String insertSql = "INSERT INTO users (full_name, email, password, phone, address, role) VALUES (?, ?, ?, ?, ?, 'customer')";
        try (Connection conn = DBConnection.getConnection()) {
            try (PreparedStatement p1 = conn.prepareStatement(checkSql)) {
                p1.setString(1, u.getEmail());
                if (p1.executeQuery().next()) return false; // E-posta zaten mevcut
            }
            try (PreparedStatement p2 = conn.prepareStatement(insertSql)) {
                p2.setString(1, u.getFullName());
                p2.setString(2, u.getEmail());
                p2.setString(3, u.getPassword());
                p2.setString(4, u.getPhone());
                p2.setString(5, u.getAddress());
                return p2.executeUpdate() > 0;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY id DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setFullName(rs.getString("full_name"));
                u.setEmail(rs.getString("email"));
                u.setPhone(rs.getString("phone"));
                u.setRole(rs.getString("role"));
                u.setCreatedAt(rs.getTimestamp("created_at"));
                list.add(u);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    // --- KATEGORİ İŞLEMLERİ ---
    public List<Category> getCategories(boolean onlyActive) {
        List<Category> list = new ArrayList<>();
        String sql = onlyActive ? "SELECT * FROM categories WHERE is_active = 1" : "SELECT * FROM categories";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Category c = new Category();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                c.setDescription(rs.getString("description"));
                c.setActive(rs.getBoolean("is_active"));
                list.add(c);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean addCategory(Category c) {
        String sql = "INSERT INTO categories (name, description, is_active) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getName());
            ps.setString(2, c.getDescription());
            ps.setBoolean(3, c.is_active());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean updateCategory(Category c) {
        String sql = "UPDATE categories SET name=?, description=?, is_active=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getName());
            ps.setString(2, c.getDescription());
            ps.setBoolean(3, c.is_active());
            ps.setInt(4, c.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public void deleteCategoryOrPassive(int id) {
        String checkSql = "SELECT id FROM products WHERE category_id = ?";
        try (Connection conn = DBConnection.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(checkSql)) {
                ps.setInt(1, id);
                if (ps.executeQuery().next()) {
                    // Bağlı ürün var, silme pasif yap! (İster 6.3 gereği)
                    String updateSql = "UPDATE categories SET is_active = 0 WHERE id = ?";
                    try (PreparedStatement ps2 = conn.prepareStatement(updateSql)) {
                        ps2.setInt(1, id);
                        ps2.executeUpdate();
                    }
                } else {
                    String deleteSql = "DELETE FROM categories WHERE id = ?";
                    try (PreparedStatement ps3 = conn.prepareStatement(deleteSql)) {
                        ps3.setInt(1, id);
                        ps3.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // --- ÜRÜN İŞLEMLERİ ---
    public List<Product> getProducts(Integer catId, boolean onlyActive) {
        List<Product> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT p.*, c.name as cat_name FROM products p LEFT JOIN categories c ON p.category_id = c.id WHERE 1=1");
        if (onlyActive) sql.append(" AND p.is_active = 1");
        if (catId != null) sql.append(" AND p.category_id = ").append(catId);
        sql.append(" ORDER BY p.id DESC");

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString());
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setCategoryId(rs.getInt("category_id"));
                p.setCategoryName(rs.getString("cat_name"));
                p.setName(rs.getString("name"));
                p.setDescription(rs.getString("description"));
                p.setPrice(rs.getDouble("price"));
                p.setStock(rs.getInt("stock"));
                p.setImageUrl(rs.getString("image_url"));
                p.setActive(rs.getBoolean("is_active"));
                list.add(p);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public Product getProductById(int id) {
        String sql = "SELECT p.*, c.name as cat_name FROM products p LEFT JOIN categories c ON p.category_id = c.id WHERE p.id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Product p = new Product();
                    p.setId(rs.getInt("id"));
                    p.setCategoryId(rs.getInt("category_id"));
                    p.setCategoryName(rs.getString("cat_name"));
                    p.setName(rs.getString("name"));
                    p.setDescription(rs.getString("description"));
                    p.setPrice(rs.getDouble("price"));
                    p.setStock(rs.getInt("stock"));
                    p.setImageUrl(rs.getString("image_url"));
                    p.setActive(rs.getBoolean("is_active"));
                    return p;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean addProduct(Product p) {
        String sql = "INSERT INTO products (category_id, name, description, price, stock, image_url, is_active) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getCategoryId());
            ps.setString(2, p.getName());
            ps.setString(3, p.getDescription());
            ps.setDouble(4, p.getPrice());
            ps.setInt(5, p.getStock());
            ps.setString(6, p.getImageUrl());
            ps.setBoolean(7, p.is_active());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public boolean updateProduct(Product p) {
        String sql = "UPDATE products SET category_id=?, name=?, description=?, price=?, stock=?, image_url=?, is_active=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getCategoryId());
            ps.setString(2, p.getName());
            ps.setString(3, p.getDescription());
            ps.setDouble(4, p.getPrice());
            ps.setInt(5, p.getStock());
            ps.setString(6, p.getImageUrl());
            ps.setBoolean(7, p.is_active());
            ps.setInt(8, p.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    public void deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // --- SİPARİŞ İŞLEMLERİ (CRITICAL TRANSACTION MANAGEMENT) ---
    public boolean createOrder(int userId, List<CartItem> cart, double totalAmount) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); // Transaction başlatılıyor!

            // 1. Sipariş kaydı oluştur
            String orderSql = "INSERT INTO orders (user_id, total_amount, status) VALUES (?, ?, 'Beklemede')";
            int orderId = 0;
            try (PreparedStatement ps = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, userId);
                ps.setDouble(2, totalAmount);
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) orderId = rs.getInt(1);
                }
            }

            // 2. Sipariş kalemlerini kaydet ve Stok düş (İster 5.7)
            String itemSql = "INSERT INTO order_items (order_id, product_id, quantity, unit_price, subtotal) VALUES (?, ?, ?, ?, ?)";
            String stockSql = "UPDATE products SET stock = stock - ? WHERE id = ?";
            
            try (PreparedStatement psItem = conn.prepareStatement(itemSql);
                 PreparedStatement psStock = conn.prepareStatement(stockSql)) {
                
                for (CartItem item : cart) {
                    // Kalem kaydet
                    psItem.setInt(1, orderId);
                    psItem.setInt(2, item.getProduct().getId());
                    psItem.setInt(3, item.getQuantity());
                    psItem.setDouble(4, item.getProduct().getPrice());
                    psItem.setDouble(5, item.getSubtotal());
                    psItem.addBatch();

                    // Stok azaltma ayarı
                    psStock.setInt(1, item.getQuantity());
                    psStock.setInt(2, item.getProduct().getId());
                    psStock.addBatch();
                }
                psItem.executeBatch();
                psStock.executeBatch();
            }

            conn.commit(); // Her şey tamamsa kaydet
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
        return false;
    }

    public List<Order> getOrdersByUser(int userId) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY id DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order o = new Order();
                    o.setId(rs.getInt("id"));
                    o.setOrderDate(rs.getTimestamp("order_date"));
                    o.setTotalAmount(rs.getDouble("total_amount"));
                    o.setStatus(rs.getString("status"));
                    list.add(o);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<Order> getAllOrders() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT o.*, u.full_name FROM orders o JOIN users u ON o.user_id = u.id ORDER BY o.id DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("id"));
                o.setCustomerName(rs.getString("full_name"));
                o.setOrderDate(rs.getTimestamp("order_date"));
                o.setTotalAmount(rs.getDouble("total_amount"));
                o.setStatus(rs.getString("status"));
                list.add(o);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public Order getOrderDetail(int orderId) {
        Order o = null;
        String orderSql = "SELECT o.*, u.full_name FROM orders o JOIN users u ON o.user_id = u.id WHERE o.id = ?";
        String itemsSql = "SELECT oi.*, p.name FROM order_items oi LEFT JOIN products p ON oi.product_id = p.id WHERE oi.order_id = ?";
        try (Connection conn = DBConnection.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(orderSql)) {
                ps.setInt(1, orderId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        o = new Order();
                        o.setId(rs.getInt("id"));
                        o.setCustomerName(rs.getString("full_name"));
                        o.setOrderDate(rs.getTimestamp("order_date"));
                        o.setTotalAmount(rs.getDouble("total_amount"));
                        o.setStatus(rs.getString("status"));
                    }
                }
            }
            if (o != null) {
                List<OrderItem> items = new ArrayList<>();
                try (PreparedStatement ps2 = conn.prepareStatement(itemsSql)) {
                    ps2.setInt(1, orderId);
                    try (ResultSet rs2 = ps2.executeQuery()) {
                        while (rs2.next()) {
                            OrderItem oi = new OrderItem();
                            oi.setId(rs2.getInt("id"));
                            oi.setProductName(rs2.getString("name"));
                            oi.setQuantity(rs2.getInt("quantity"));
                            oi.setUnitPrice(rs2.getDouble("unit_price"));
                            oi.setSubtotal(rs2.getDouble("subtotal"));
                            items.add(oi);
                        }
                    }
                }
                o.setItems(items);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return o;
    }

    public boolean updateOrderStatus(int orderId, String status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }

    // --- PANEL ÖZET İSTATİSTİKLERİ ---
    public int getCount(String table) {
        String sql = "SELECT COUNT(*) FROM " + table;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public int getPendingOrdersCount() {
        String sql = "SELECT COUNT(*) FROM orders WHERE status = 'Beklemede'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }
}
