package hr.abysalto.hiring.mid.product.dto;

import java.math.BigDecimal;
import java.util.List;

public record DummyJsonProductResponse(
        Long id,
        String title,
        String description,
        BigDecimal price,
        Double discountPercentage,
        Double rating,
        Integer stock,
        String brand,
        String category,
        String thumbnail,
        List<String> images
) {
}