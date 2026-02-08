package hr.abysalto.hiring.mid.infrastructure.persistance.mapper;

import hr.abysalto.hiring.mid.domain.model.Product;
import hr.abysalto.hiring.mid.infrastructure.persistance.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toDomain(ProductEntity entity) {
        return new Product(entity.getId(), entity.getTitle(), entity.getPrice());
    }

    public ProductEntity toEntity(Product product) {
        return new ProductEntity(product.getId(), product.getTitle(), product.getPrice());
    }
}