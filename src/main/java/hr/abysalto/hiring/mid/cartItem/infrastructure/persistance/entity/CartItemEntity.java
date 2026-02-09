package hr.abysalto.hiring.mid.cartItem.infrastructure.persistance.entity;

import hr.abysalto.hiring.mid.product.infrastructure.persistance.entity.ProductEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItemEntity {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private ProductEntity product;

    private int quantity;
}