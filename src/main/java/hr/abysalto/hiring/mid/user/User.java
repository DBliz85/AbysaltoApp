package hr.abysalto.hiring.mid.user;

import hr.abysalto.hiring.mid.product.Product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class User {

    private Long id;
    private String username;
    private String password;
    private final Set<Product> favoriteProducts = new HashSet<>();

    public User(Long id, String username, String password) {
        if (username == null || username.isBlank()) throw new IllegalArgumentException("Username required");
        if (password == null || password.isBlank()) throw new IllegalArgumentException("Password required");
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public void addFavorite(Product product) {
        favoriteProducts.add(product);
    }

    public void removeFavorite(Long productId) {
        favoriteProducts.removeIf(p -> p.getId().equals(productId));
    }

    public List<Product> getFavorites() {
        return new ArrayList<>(favoriteProducts);
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password;}
}