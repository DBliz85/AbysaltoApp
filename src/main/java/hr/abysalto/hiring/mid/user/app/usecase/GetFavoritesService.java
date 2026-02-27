package hr.abysalto.hiring.mid.user.app.usecase;

import hr.abysalto.hiring.mid.shared.readmodel.ProductView;
import hr.abysalto.hiring.mid.user.app.port.out.ProductGateway;
import hr.abysalto.hiring.mid.user.domain.User;
import hr.abysalto.hiring.mid.user.domain.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetFavoritesService implements GetFavoritesUseCase {

    private final UserRepository userRepository;
    private final ProductGateway productGateway;

    public GetFavoritesService(UserRepository userRepository, ProductGateway productGateway) {
        this.userRepository = userRepository;
        this.productGateway = productGateway;
    }

    @Override
    public List<ProductView> getFavorites(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return productGateway.getProducts(user.getFavoriteProductIds());    }
}
