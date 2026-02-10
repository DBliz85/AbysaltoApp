package hr.abysalto.hiring.mid.product.domain.repository;

import hr.abysalto.hiring.mid.product.domain.Product;

public interface ProductRepository {
    Product save(Product product);
}