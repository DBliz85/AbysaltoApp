package hr.abysalto.hiring.mid.product;

import java.math.BigDecimal;

public record ProductDto(Long id, String name, BigDecimal price) {
}
