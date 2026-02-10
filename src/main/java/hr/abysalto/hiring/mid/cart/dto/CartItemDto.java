package hr.abysalto.hiring.mid.cart.dto;

import hr.abysalto.hiring.mid.product.dto.ProductDto;

import java.math.BigDecimal;

public record CartItemDto(
        Long productId,
        String productName,
        BigDecimal price,
        Integer quantity
) {}