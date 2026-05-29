package com.ecommerce.model;

public class CartItem {
    private Product product;
    private int quantity;
    private double subtotal;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.subtotal = product.getPrice() * quantity;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { 
        this.quantity = quantity; 
        this.subtotal = this.product.getPrice() * quantity;
    }
    public double getSubtotal() { return subtotal; }
}
