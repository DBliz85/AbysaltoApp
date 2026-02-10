package hr.abysalto.hiring.mid.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message = "Product title cannot be blank")
        String title,

        @NotNull(message = "Price cannot be null")
        BigDecimal price
) {}