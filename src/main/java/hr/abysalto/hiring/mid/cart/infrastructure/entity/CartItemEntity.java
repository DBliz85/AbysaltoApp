package hr.abysalto.hiring.mid.cart.infrastructure.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private CartEntity cart;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(nullable = false)
    private int quantity;

    public CartItemEntity() {}

    public CartItemEntity(
            Long productId,
            int quantity
    ) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getId() { return id; }
    public CartEntity getCart() { return cart; }
    public Long getProductId() { return productId; }
    public int getQuantity() { return quantity; }
    public void setCart(CartEntity cart) { this.cart = cart; }
}