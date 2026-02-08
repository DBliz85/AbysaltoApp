package hr.abysalto.hiring.mid.app.dto;

import java.math.BigDecimal;

public record ProductDto(Long id, String title, BigDecimal price) {
}
