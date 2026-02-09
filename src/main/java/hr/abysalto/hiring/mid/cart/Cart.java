package hr.abysalto.hiring.mid.cart;

import hr.abysalto.hiring.mid.product.Product;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Cart {

    private final Long userId;
    private final Set<CartItem> items = new HashSet<>();

    public Cart(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() { return userId; }
    public Set<CartItem> getItems() { return Set.copyOf(items); }

    public void addItem(Product product, int quantity) {
        CartItem item = items.stream()
                .filter(i -> i.getProduct().equals(product))
                .findFirst()
                .orElseGet(() -> {
                    CartItem newItem = new CartItem(this, product, 0);
                    items.add(newItem);
                    return newItem;
                });
        item.increaseQuantity(quantity);
    }

    public void removeItem(Long productId) {
        items.removeIf(i -> i.getProduct().getId().equals(productId));
    }

    public boolean isEmpty() { return items.isEmpty(); }

    public Optional<CartItem> findItem(Long productId) {
        return items.stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst();
    }
}