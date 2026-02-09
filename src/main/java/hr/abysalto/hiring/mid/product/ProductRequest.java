package hr.abysalto.hiring.mid.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message = "Product name cannot be blank")
        String name,

        @NotNull(message = "Price cannot be null")
        BigDecimal price
) {}