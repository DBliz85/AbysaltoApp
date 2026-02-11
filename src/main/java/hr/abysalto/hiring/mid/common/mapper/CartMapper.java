package hr.abysalto.hiring.mid.common.mapper;

import hr.abysalto.hiring.mid.cart.domain.Cart;
import hr.abysalto.hiring.mid.cart.domain.CartItem;
import hr.abysalto.hiring.mid.cart.dto.CartDto;
import hr.abysalto.hiring.mid.cart.dto.CartItemResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartMapper {

    public CartDto toResponse(Cart cart) {
        List<CartItemResponse> items = cart.getItems().stream()
                .map(this::toItemResponse)
                .toList();

        return new CartDto(null, items);
    }

    private CartItemResponse toItemResponse(CartItem item) {
        return new CartItemResponse(
                item.getProductId(),
                item.getQuantity()
        );
    }
}