package hr.abysalto.hiring.mid.common.mapper;

import hr.abysalto.hiring.mid.cart.Cart;
import hr.abysalto.hiring.mid.cart.CartItem;
import hr.abysalto.hiring.mid.cart.CartItemResponse;
import hr.abysalto.hiring.mid.cart.CartResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartMapper {

    public CartResponse toResponse(Cart cart) {
        List<CartItemResponse> items = cart.getItems().stream()
                .map(this::toItemResponse)
                .toList();

        return new CartResponse(null, items); // null cartId for domain Cart
    }

    private CartItemResponse toItemResponse(CartItem item) {
        return new CartItemResponse(
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getProduct().getPrice()
        );
    }
}