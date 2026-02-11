package hr.abysalto.hiring.mid.user.app.usecase;

import hr.abysalto.hiring.mid.product.app.usecase.exception.dto.ProductNotFoundException;
import hr.abysalto.hiring.mid.shared.readmodel.ProductView;
import hr.abysalto.hiring.mid.user.app.port.out.ProductGateway;
import hr.abysalto.hiring.mid.user.domain.User;
import hr.abysalto.hiring.mid.user.domain.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    private final UserRepository userRepository;
    private final ProductGateway productGateway;

    public FavoriteService(UserRepository userRepository, ProductGateway productGateway) {
        this.userRepository = userRepository;
        this.productGateway = productGateway;
    }

    public void addFavorite(String username, Long productId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        var productExists = productGateway.exists(productId);
        if (!productExists) {
            throw new ProductNotFoundException(productId);
        }

        user.addFavorite(productId);
        userRepository.save(user);
    }

    public void removeFavorite(String username, Long productId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.removeFavorite(productId);
        userRepository.save(user);
    }

    public List<ProductView> getFavorites(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return productGateway.getProducts(user.getFavoriteProductIds());
    }
}