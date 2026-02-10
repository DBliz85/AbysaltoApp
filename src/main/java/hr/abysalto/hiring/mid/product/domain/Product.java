package hr.abysalto.hiring.mid.product.domain;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Product {

    private final Long id;
    private final String title;
    private final BigDecimal price;

    public Product(Long id, String title, BigDecimal price) {
        if (title == null || title.isBlank()) throw new IllegalArgumentException("Product title must not be blank");
        if (price == null) throw new IllegalArgumentException("Price must not be null");

        this.id = id;
        this.title = title;
        this.price = price;
    }
}