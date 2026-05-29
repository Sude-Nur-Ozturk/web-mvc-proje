package com.ecommerce.controller;

import com.ecommerce.dao.AppDAO;
import com.ecommerce.model.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "AdminController", urlPatterns = {
    "/admin/dashboard", 
    "/admin/categories", "/admin/category-form", "/admin/category-delete",
    "/admin/products", "/admin/product-form", "/admin/product-delete",
    "/admin/orders", "/admin/order-detail", "/admin/users"
})
public class AdminController extends HttpServlet {
    private AppDAO dao;

    @Override
    public void init() { dao = new AppDAO(); }

    // Merkezi Güvenlik Filtresi (İster 6.1)
    private boolean checkAdmin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User u = (User) req.getSession().getAttribute("user");
        if (u == null || !"admin".equals(u.getRole())) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return false;
        }
        return true;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (!checkAdmin(req, resp)) return;
        String path = req.getServletPath();

        switch (path) {
            case "/admin/dashboard":
                req.setAttribute("pCount", dao.getCount("products"));
                req.setAttribute("cCount", dao.getCount("categories"));
                req.setAttribute("uCount", dao.getCount("users"));
                req.setAttribute("oCount", dao.getCount("orders"));
                req.setAttribute("pendingCount", dao.getPendingOrdersCount());
                req.getRequestDispatcher("/admin/dashboard.jsp").forward(req, resp);
                break;
            case "/admin/categories":
                req.setAttribute("categories", dao.getCategories(false));
                req.getRequestDispatcher("/admin/categories.jsp").forward(req, resp);
                break;
            case "/admin/category-form":
                String catId = req.getParameter("id");
                if (catId != null) {
                    req.setAttribute("category", dao.getCategories(false).stream().filter(c -> c.getId() == Integer.parseInt(catId)).findFirst().orElse(null));
                }
                req.getRequestDispatcher("/admin/category-form.jsp").forward(req, resp);
                break;
            case "/admin/category-delete":
                dao.deleteCategoryOrPassive(Integer.parseInt(req.getParameter("id")));
                resp.sendRedirect("categories");
                break;
            case "/admin/products":
                req.setAttribute("products", dao.getProducts(null, false));
                req.getRequestDispatcher("/admin/products.jsp").forward(req, resp);
                break;
            case "/admin/product-form":
                req.setAttribute("categories", dao.getCategories(true));
                String prodId = req.getParameter("id");
                if (prodId != null) {
                    req.setAttribute("product", dao.getProductById(Integer.parseInt(prodId)));
                }
                req.getRequestDispatcher("/admin/product-form.jsp").forward(req, resp);
                break;
            case "/admin/product-delete":
                dao.deleteProduct(Integer.parseInt(req.getParameter("id")));
                resp.sendRedirect("products");
                break;
            case "/admin/orders":
                req.setAttribute("orders", dao.getAllOrders());
                req.getRequestDispatcher("/admin/orders.jsp").forward(req, resp);
                break;
            case "/admin/order-detail":
                req.setAttribute("order", dao.getOrderDetail(Integer.parseInt(req.getParameter("id"))));
                req.getRequestDispatcher("/admin/order-detail.jsp").forward(req, resp);
                break;
            case "/admin/users":
                req.setAttribute("users", dao.getAllUsers());
                req.getRequestDispatcher("/admin/users.jsp").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (!checkAdmin(req, resp)) return;
        String path = req.getServletPath();

        if ("/admin/category-form".equals(path)) {
            saveCategory(req, resp);
        } else if ("/admin/product-form".equals(path)) {
            saveProduct(req, resp);
        } else if ("/admin/order-detail".equals(path)) {
            dao.updateOrderStatus(Integer.parseInt(req.getParameter("orderId")), req.getParameter("status"));
            resp.sendRedirect("orders");
        }
    }

    private void saveCategory(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String idStr = req.getParameter("id");
        String name = req.getParameter("name");
        String desc = req.getParameter("description");
        boolean active = req.getParameter("isActive") != null;

        Category c = new Category();
        c.setName(name);
        c.setDescription(desc);
        c.setActive(active);

        if (name == null || name.trim().isEmpty()) {
            req.setAttribute("error", "Kategori adı boş olamaz.");
            req.setAttribute("category", c);
            req.getRequestDispatcher("/admin/category-form.jsp").forward(req, resp);
            return;
        }

        if (idStr != null && !idStr.isEmpty()) {
            c.setId(Integer.parseInt(idStr));
            dao.updateCategory(c);
        } else {
            dao.addCategory(c);
        }
        resp.sendRedirect("categories");
    }

    private void saveProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String idStr = req.getParameter("id");
        String name = req.getParameter("name");
        String priceStr = req.getParameter("price");
        String stockStr = req.getParameter("stock");
        String catStr = req.getParameter("categoryId");

        // Sunucu doğrulaması (İster 6.4)
        if (name == null || name.isEmpty() || priceStr.isEmpty() || stockStr.isEmpty() || catStr == null) {
            req.setAttribute("error", "Lütfen zorunlu alanları doldurunuz.");
            req.setAttribute("categories", dao.getCategories(true));
            req.getRequestDispatcher("/admin/product-form.jsp").forward(req, resp);
            return;
        }

        double price = Double.parseDouble(priceStr);
        int stock = Integer.parseInt(stockStr);

        if (price <= 0 || stock < 0) {
            req.setAttribute("error", "Fiyat 0'dan büyük, stok negatiften küçük olamaz.");
            req.setAttribute("categories", dao.getCategories(true));
            req.getRequestDispatcher("/admin/product-form.jsp").forward(req, resp);
            return;
        }

        Product p = new Product();
        p.setCategoryId(Integer.parseInt(catStr));
        p.setName(name);
        p.setDescription(req.getParameter("description"));
        p.setPrice(price);
        p.setStock(stock);
        p.setImageUrl(req.getParameter("imageUrl"));
        p.setActive(req.getParameter("isActive") != null);

        if (idStr != null && !idStr.isEmpty()) {
            p.setId(Integer.parseInt(idStr));
            dao.updateProduct(p);
        } else {
            dao.addProduct(p);
        }
        resp.sendRedirect("products");
    }
}
