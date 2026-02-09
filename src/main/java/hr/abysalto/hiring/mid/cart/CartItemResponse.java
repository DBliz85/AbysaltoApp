package hr.abysalto.hiring.mid.cart;

import java.math.BigDecimal;

public record CartItemResponse(
        Long productId,
        String productName,
        int quantity,
        BigDecimal price
) {
}
