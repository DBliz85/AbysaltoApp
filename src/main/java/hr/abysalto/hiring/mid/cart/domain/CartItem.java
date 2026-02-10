package hr.abysalto.hiring.mid.cart.domain;

import java.math.BigDecimal;

public class CartItem {

    private final Long id;
    private final Long productId;
    private final String productName;
    private final BigDecimal price;
    private final int quantity;

    public CartItem(Long id, Long productId, String productName, BigDecimal price, int quantity) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public static CartItem fromProduct(
            Long productId,
            String productName,
            BigDecimal price,
            int quantity
    ) {
        return new CartItem(
                null,
                productId,
                productName,
                price,
                quantity
        );
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public CartItem increaseQuantity(int amount) {
        return new CartItem(
                this.id,
                this.productId,
                this.productName,
                this.price,
                this.quantity + amount
        );
    }
}