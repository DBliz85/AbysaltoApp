package hr.abysalto.hiring.mid.cart.domain;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private Long id;
    private Long userId;
    private List<CartItem> items = new ArrayList<>();

    public Cart(Long id, Long userId, List<CartItem> items) {
        this.id = id;
        this.userId = userId;
        if (items != null) this.items = items;
    }

    public Cart(Long userId) {
        this.userId = userId;
    }

    public void addItem(Long productId, int quantity) {
        CartItem existing = items.stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .orElse(null);

        if (existing != null) {
            existing.increaseQuantity(quantity);
        } else {
            items.add(new CartItem(productId, quantity));
        }
    }

    public void removeItem(Long productId) {
        items.removeIf(item -> item.getProductId().equals(productId));
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public List<CartItem> getItems() { return items; }
}