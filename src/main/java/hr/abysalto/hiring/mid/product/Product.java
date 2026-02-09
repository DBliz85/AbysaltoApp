package hr.abysalto.hiring.mid.product;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Product {

    private final Long id;
    private final String name;
    private final BigDecimal price;

    public Product(Long id, String name, BigDecimal price) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Product name must not be blank");
        if (price == null) throw new IllegalArgumentException("Price must not be null");

        this.id = id;
        this.name = name;
        this.price = price;
    }
}