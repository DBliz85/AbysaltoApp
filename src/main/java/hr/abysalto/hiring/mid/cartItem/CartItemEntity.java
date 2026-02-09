package hr.abysalto.hiring.mid.cartItem;

import hr.abysalto.hiring.mid.product.ProductEntity;
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