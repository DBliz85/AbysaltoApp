package hr.abysalto.hiring.mid.infrastructure.persistance.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cart_items")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItemEntity {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private ProductEntity product;

    private int quantity;
}