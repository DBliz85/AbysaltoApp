package hr.abysalto.hiring.mid.common.mapper;

import hr.abysalto.hiring.mid.product.Product;
import hr.abysalto.hiring.mid.product.ProductEntity;
import hr.abysalto.hiring.mid.product.ProductResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    private ProductMapper() {}

    public static ProductResponse toResponse(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product must not be null");
        }
        return new ProductResponse(product.getId(), product.getName(), product.getPrice());
    }

    public static ProductEntity toEntity(Product product) {
        if (product == null) return null;
        return new ProductEntity(
                null,
                product.getName(),
                product.getPrice()
        );
    }

    public static Product toDomain(ProductEntity entity) {
        return new Product(entity.getId(), entity.getName(), entity.getPrice());
    }
}