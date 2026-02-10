package hr.abysalto.hiring.mid.shared.readmodel;

import java.math.BigDecimal;

public record ProductView(Long id, String title, BigDecimal price) {
}
