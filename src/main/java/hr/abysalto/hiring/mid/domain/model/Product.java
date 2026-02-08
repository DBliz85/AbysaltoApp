package hr.abysalto.hiring.mid.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Product {
    private final Long id;
    private final String title;
    private final BigDecimal price;
}
