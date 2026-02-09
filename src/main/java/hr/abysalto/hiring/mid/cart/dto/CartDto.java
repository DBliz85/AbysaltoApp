package hr.abysalto.hiring.mid.cart.dto;

import java.util.List;

public record CartDto(
        Long cartId,
        List<CartItemResponse> items
) {}
