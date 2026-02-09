package hr.abysalto.hiring.mid.cart.dto;

public record AddToCartRequest(
        Long productId,
        int quantity
) {}