package hr.abysalto.hiring.mid.user.domain;

import java.util.HashSet;
import java.util.Set;

public final class User {

    private Long id;
    private String username;
    private String password;
    private final Set<Long> favoriteProductIds = new HashSet<>();

    public User(Long id, String username, String password) {
        if (username == null || username.isBlank()) throw new IllegalArgumentException("Username required");
        if (password == null || password.isBlank()) throw new IllegalArgumentException("Password required");
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public void addFavorite(Long productId) {
        favoriteProductIds.add(productId);
    }

    public void removeFavorite(Long productId) {
        favoriteProductIds.remove(productId);
    }

    public Set<Long> getFavoriteProductIds() {
        return new HashSet<>(favoriteProductIds);
    }

    public void overwriteFavorites(Set<Long> favoriteProductIds) {
        this.favoriteProductIds.clear();
        this.favoriteProductIds.addAll(favoriteProductIds);
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password;}
}