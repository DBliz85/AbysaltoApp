package hr.abysalto.hiring.mid.user.app.usecase;

import hr.abysalto.hiring.mid.favorites.dto.exception.ProductNotInFavoritesException;
import hr.abysalto.hiring.mid.user.domain.User;
import hr.abysalto.hiring.mid.user.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class RemoveProductFromFavoriteService implements RemoveProductFromFavoriteUse {
    private final UserRepository userRepository;

    public RemoveProductFromFavoriteService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void removeFavorite(String username, Long productId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getFavoriteProductIds().contains(productId)) {
            throw new ProductNotInFavoritesException(productId);
        }

        user.removeFavorite(productId);
        userRepository.save(user);
    }
}
