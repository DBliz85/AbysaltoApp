package hr.abysalto.hiring.mid.cart.mapper;

import hr.abysalto.hiring.mid.cart.domain.Cart;
import hr.abysalto.hiring.mid.cart.domain.CartItem;
import hr.abysalto.hiring.mid.cart.dto.CartDto;
import hr.abysalto.hiring.mid.cart.dto.CartItemResponse;

public class CartMapper {

    private CartMapper() {}

    public static CartDto toResponse(Cart cart) {
        if (cart == null) return null;

        return new CartDto(
                cart.getId(),
                cart.getItems().stream()
                        .map(CartMapper::toItemResponse)
                        .toList()
        );
    }

    private static CartItemResponse toItemResponse(CartItem item) {
        return new CartItemResponse(
                item.getProductId(),
                item.getQuantity()
        );
    }
}