package hr.abysalto.hiring.mid.cart.app.usecase;

import hr.abysalto.hiring.mid.cart.app.port.out.UserGateway;
import hr.abysalto.hiring.mid.cart.domain.Cart;
import hr.abysalto.hiring.mid.cart.domain.CartRepository;
import org.springframework.stereotype.Service;

@Service
public class GetCartService implements GetCartUseCase {
    private final CartRepository cartRepository;
    private final UserGateway userGateway;

    public GetCartService(CartRepository cartRepository, UserGateway userGateway) {
        this.cartRepository = cartRepository;
        this.userGateway = userGateway;
    }

    @Override
    public Cart getCart(String username) {
        Long userId = userGateway.getUserIdByUsername(username);

        return cartRepository.findByUserId(userId)
                .orElseGet(() -> cartRepository.save(new Cart(userId)));
    }
}
