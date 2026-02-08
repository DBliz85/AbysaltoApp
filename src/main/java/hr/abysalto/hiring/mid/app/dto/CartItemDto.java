package hr.abysalto.hiring.mid.app.dto;

import java.math.BigDecimal;

public record CartItemDto(ProductDto product, int quantity, BigDecimal totalPrice) {
}