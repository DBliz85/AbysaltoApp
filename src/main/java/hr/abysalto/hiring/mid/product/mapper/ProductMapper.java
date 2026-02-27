package hr.abysalto.hiring.mid.product.mapper;

import hr.abysalto.hiring.mid.product.domain.Product;
import hr.abysalto.hiring.mid.product.dto.ProductDto;
import hr.abysalto.hiring.mid.product.infrastructure.persistence.entity.ProductEntity;

public class ProductMapper {

    private ProductMapper() {}

    public static ProductDto toDto(Product product) {
        if (product == null) return null;

        return new ProductDto(
                product.getId(),
                product.getTitle(),
                product.getPrice()
        );
    }

    public static ProductEntity toEntity(Product product) {
        if (product == null) return null;
        return new ProductEntity(
                product.getId(),
                product.getTitle(),
                product.getPrice()
        );
    }

    public static Product toDomain(ProductEntity entity) {
        if (entity == null) return null;
        return new Product(entity.getId(), entity.getTitle(), entity.getPrice());
    }
}