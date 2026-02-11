package hr.abysalto.hiring.mid.cart.dto;

public record CartItemResponse(
        Long productId,
        int quantity
) {
}
