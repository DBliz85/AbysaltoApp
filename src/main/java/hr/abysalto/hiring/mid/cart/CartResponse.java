package hr.abysalto.hiring.mid.cart;

import java.util.List;

public record CartResponse(
        Long cartId,
        List<CartItemResponse> items
) {}
