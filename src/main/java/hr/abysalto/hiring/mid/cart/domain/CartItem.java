package hr.abysalto.hiring.mid.cart.domain;

import hr.abysalto.hiring.mid.product.domain.Product;

public class CartItem {

    private final Cart cart;
    private final Product product;
    private int quantity;

    public CartItem(Cart cart, Product product, int quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }

    public void increaseQuantity(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        this.quantity += amount;
    }

    public void setQuantity(int quantity) {
        if (quantity < 1) throw new IllegalArgumentException("Quantity must be >= 1");
        this.quantity = quantity;
    }
}