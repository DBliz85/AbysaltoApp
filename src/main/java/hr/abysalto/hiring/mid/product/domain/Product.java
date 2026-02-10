package hr.abysalto.hiring.mid.product.domain;

import java.math.BigDecimal;

public class Product {
    private Long id;
    private String title;
    private BigDecimal price;

    public Product(Long id, String title, BigDecimal price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public BigDecimal getPrice() { return price; }
}