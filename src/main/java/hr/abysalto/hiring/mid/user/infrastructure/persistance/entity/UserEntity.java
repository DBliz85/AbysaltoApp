package hr.abysalto.hiring.mid.user.infrastructure.persistance.entity;


import hr.abysalto.hiring.mid.product.infrastructure.persistance.entity.ProductEntity;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_favorites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<ProductEntity> favoriteProducts = new HashSet<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Set<ProductEntity> getFavoriteProducts() { return favoriteProducts; }
    public void setFavoriteProducts(Set<ProductEntity> favoriteProducts) {
        this.favoriteProducts = favoriteProducts;
    }

    public void addFavorite(ProductEntity product) {
        favoriteProducts.add(product);
    }

    public void removeFavorite(Long productId) {
        favoriteProducts.removeIf(p -> p.getId().equals(productId));
    }
}