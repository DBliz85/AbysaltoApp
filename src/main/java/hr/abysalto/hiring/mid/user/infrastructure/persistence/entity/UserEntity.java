package hr.abysalto.hiring.mid.user.infrastructure.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty(message = "Username is mandatory")
    @Column(unique = true, nullable = false)
    private String username;
    @NotEmpty(message = "Password is mandatory")
    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_favorites",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "product_id")
    private Set<Long> favoriteProductIds = new HashSet<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Set<Long> getFavoriteProductIds() { return favoriteProductIds; }
    public void setFavoriteProductIds(Set<Long> favoriteProductIds) {
        this.favoriteProductIds = favoriteProductIds;
    }

    public void addFavorite(Long productId) {
        favoriteProductIds.add(productId);
    }

    public void removeFavorite(Long productId) {
        favoriteProductIds.remove(productId);
    }
}