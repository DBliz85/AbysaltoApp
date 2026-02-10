package hr.abysalto.hiring.mid.cart.domain;

import hr.abysalto.hiring.mid.product.dto.ProductDto;

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

    public void addItem(ProductDto productDto, int quantity) {
        if (this.id == null) {
            throw new IllegalStateException("Cart ID must not be null before adding items");
        }

        CartItem existing = items.stream()
                .filter(i -> i.getProductId().equals(productDto.id()))
                .findFirst()
                .orElse(null);

        if (existing != null) {
            items.remove(existing);
            items.add(existing.increaseQuantity(quantity));
        } else {
            items.add(CartItem.fromProduct(
                    productDto.id(),
                    productDto.title(),
                    productDto.price(),
                    quantity
            ));
        }
    }

    public void removeItem(Long productId) {
        items.removeIf(item -> item.getProductId().equals(productId));
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public List<CartItem> getItems() {
        return items;
    }
}