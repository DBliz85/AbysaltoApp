package hr.abysalto.hiring.mid.favorites.app.usecase;

import hr.abysalto.hiring.mid.common.mapper.ProductMapper;
import hr.abysalto.hiring.mid.product.infrastructure.persistance.SpringProductJpaRepository;
import hr.abysalto.hiring.mid.user.infrastructure.persistance.SpringUserJpaRepository;
import hr.abysalto.hiring.mid.product.domain.Product;
import hr.abysalto.hiring.mid.user.infrastructure.persistance.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    private final SpringUserJpaRepository userRepository;
    private final SpringProductJpaRepository productRepository;

    public FavoriteService(SpringUserJpaRepository userRepository, SpringProductJpaRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void addFavorite(String username, Long productId) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        var productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        user.getFavoriteProducts().add(productEntity);
        userRepository.save(user);
    }

    @Transactional
    public void removeFavorite(String username, Long productId) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.getFavoriteProducts().removeIf(p -> p.getId().equals(productId));
        userRepository.save(user);
    }

    @Transactional
    public List<Product> getFavorites(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return user.getFavoriteProducts().stream()
                .map(ProductMapper::toDomain)
                .collect(Collectors.toList());
    }
}