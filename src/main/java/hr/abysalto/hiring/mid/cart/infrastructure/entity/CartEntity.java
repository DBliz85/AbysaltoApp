package hr.abysalto.hiring.mid.cart.infrastructure.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItemEntity> items = new ArrayList<>();

    public CartEntity() {}

    public CartEntity(Long userId) {
        this.userId = userId;
    }

    public CartEntity(Long id, Long userId) {
        this.id = id;
        this.userId = userId;
    }

    public void addItem(CartItemEntity item) {
        items.add(item);
        item.setCart(this);
    }

    public void removeItem(CartItemEntity item) {
        items.remove(item);
        item.setCart(null);
    }

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public List<CartItemEntity> getItems() { return items; }
}