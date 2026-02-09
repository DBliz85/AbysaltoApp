package hr.abysalto.hiring.mid.cart.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "carts")
public class JpaCartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Getter
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<JpaCartItemEntity> items = new HashSet<>();

    protected JpaCartEntity() {}

    public JpaCartEntity(Long userId) { this.userId = userId; }
}