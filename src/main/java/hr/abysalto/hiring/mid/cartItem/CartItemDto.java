package hr.abysalto.hiring.mid.cartItem;

import hr.abysalto.hiring.mid.product.dto.ProductDto;

import java.math.BigDecimal;

public record CartItemDto(ProductDto product, int quantity, BigDecimal totalPrice) {
}