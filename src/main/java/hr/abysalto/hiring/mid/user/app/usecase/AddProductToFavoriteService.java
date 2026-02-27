package hr.abysalto.hiring.mid.user.app.usecase;

import hr.abysalto.hiring.mid.product.app.usecase.exception.ProductNotFoundException;
import hr.abysalto.hiring.mid.user.app.port.out.ProductGateway;
import hr.abysalto.hiring.mid.user.app.usecase.exception.UserNotFoundException;
import hr.abysalto.hiring.mid.user.domain.User;
import hr.abysalto.hiring.mid.user.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AddProductToFavoriteService implements AddProductToFavoriteUseCase {
    private final UserRepository userRepository;
    private final ProductGateway productGateway;

    public AddProductToFavoriteService(UserRepository userRepository, ProductGateway productGateway) {
        this.userRepository = userRepository;
        this.productGateway = productGateway;
    }

    @Override
    public void addFavorite(String username, Long productId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        var productExists = productGateway.exists(productId);
        if (!productExists) {
            throw new ProductNotFoundException(productId);
        }

        user.addFavorite(productId);
        userRepository.save(user);
    }
}
