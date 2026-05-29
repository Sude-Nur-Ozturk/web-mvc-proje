package com.ecommerce.controller;

import com.ecommerce.dao.AppDAO;
import com.ecommerce.model.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "MainController", urlPatterns = {"/", "/product", "/login", "/logout", "/register", "/cart", "/checkout", "/my-orders"})
public class MainController extends HttpServlet {
    private AppDAO dao;

    @Override
    public void init() { dao = new AppDAO(); }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String path = req.getServletPath();

        switch (path) {
            case "/":
                showHome(req, resp);
                break;
            case "/product":
                showProductDetail(req, resp);
                break;
            case "/login":
                req.getRequestDispatcher("login.jsp").forward(req, resp);
                break;
            case "/register":
                req.getRequestDispatcher("register.jsp").forward(req, resp);
                break;
            case "/cart":
                showCart(req, resp);
                break;
            case "/my-orders":
                showMyOrders(req, resp);
                break;
            case "/logout":
                req.getSession().invalidate();
                resp.sendRedirect(req.getContextPath() + "/");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String path = req.getServletPath();

        if ("/login".equals(path)) {
            handleLogin(req, resp);
        } else if ("/register".equals(path)) {
            handleRegister(req, resp);
        } else if ("/cart".equals(path)) {
            handleCartUpdates(req, resp);
        } else if ("/checkout".equals(path)) {
            handleCheckout(req, resp);
        }
    }

    private void showHome(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String catParam = req.getParameter("category");
        Integer catId = (catParam != null && !catParam.isEmpty()) ? Integer.parseInt(catParam) : null;
        
        req.setAttribute("products", dao.getProducts(catId, true));
        req.setAttribute("categories", dao.getCategories(true));
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    private void showProductDetail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("product", dao.getProductById(id));
        req.getRequestDispatcher("product-detail.jsp").forward(req, resp);
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String pass = req.getParameter("password");

        // Sunucu Tarafı Form Doğrulaması (İster 11)
        if (email == null || pass == null || email.trim().isEmpty() || pass.trim().isEmpty()) {
            req.setAttribute("error", "E-posta ve şifre alanları boş bırakılamaz.");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }

        User u = dao.login(email, pass);
        if (u != null) {
            HttpSession session = req.getSession();
            session.setAttribute("user", u);
            if ("admin".equals(u.getRole())) {
                resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
            } else {
                resp.sendRedirect(req.getContextPath() + "/");
            }
        } else {
            req.setAttribute("error", "Hatalı E-posta veya Şifre!");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }

    private void handleRegister(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("fullName");
        String email = req.getParameter("email");
        String pass = req.getParameter("password");
        
        if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            req.setAttribute("error", "Lütfen zorunlu alanları doldurun.");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }

        User u = new User();
        u.setFullName(name);
        u.setEmail(email);
        u.setPassword(pass);
        u.setPhone(req.getParameter("phone"));
        u.setAddress(req.getParameter("address"));

        if (dao.register(u)) {
            resp.sendRedirect(req.getContextPath() + "/login?success=1");
        } else {
            req.setAttribute("error", "Bu e-posta adresi zaten kayıtlı!");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }
    }

    @SuppressWarnings("unchecked")
    private void showCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();
        
        double total = 0;
        for (CartItem item : cart) total += item.getSubtotal();
        
        session.setAttribute("cartTotal", total);
        req.getRequestDispatcher("cart.jsp").forward(req, resp);
    }

    @SuppressWarnings("unchecked")
    private void handleCartUpdates(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) cart = new ArrayList<>();

        String action = req.getParameter("action");
        int prodId = Integer.parseInt(req.getParameter("id"));

        if ("add".equals(action)) {
            Product p = dao.getProductById(prodId);
            if (p != null && p.getStock() > 0) {
                boolean found = false;
                for (CartItem item : cart) {
                    if (item.getProduct().getId() == prodId) {
                        if (item.getQuantity() < p.getStock()) {
                            item.setQuantity(item.getQuantity() + 1);
                        }
                        found = true;
                        break;
                    }
                }
                if (!found) cart.add(new CartItem(p, 1));
            }
        } else if ("update".equals(action)) {
            int qty = Integer.parseInt(req.getParameter("quantity"));
            Product p = dao.getProductById(prodId);
            for (CartItem item : cart) {
                if (item.getProduct().getId() == prodId) {
                    if (qty <= p.getStock() && qty > 0) item.setQuantity(qty);
                    break;
                }
            }
        } else if ("remove".equals(action)) {
            cart.removeIf(item -> item.getProduct().getId() == prodId);
        }

        session.setAttribute("cart", cart);
        resp.sendRedirect(req.getContextPath() + "/cart");
    }

    @SuppressWarnings("unchecked")
    private void handleCheckout(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        User u = (User) session.getAttribute("user");
        
        // Giriş Yapılmadıysa Yönlendir (İster 5.5)
        if (u == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        Double total = (Double) session.getAttribute("cartTotal");

        if (cart == null || cart.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        if (dao.createOrder(u.getId(), cart, total)) {
            session.removeAttribute("cart");
            session.removeAttribute("cartTotal");
            req.setAttribute("msg", "Siparişiniz başarıyla oluşturuldu.");
            req.getRequestDispatcher("cart.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/cart?error=1");
        }
    }

    private void showMyOrders(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = (User) req.getSession().getAttribute("user");
        if (u == null) { resp.sendRedirect(req.getContextPath() + "/login"); return; }
        
        req.setAttribute("orders", dao.getOrdersByUser(u.getId()));
        req.getRequestDispatcher("my-orders.jsp").forward(req, resp);
    }
}
