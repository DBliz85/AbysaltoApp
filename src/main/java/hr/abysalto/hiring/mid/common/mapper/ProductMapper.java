package hr.abysalto.hiring.mid.common.mapper;

import hr.abysalto.hiring.mid.product.domain.Product;
import hr.abysalto.hiring.mid.product.dto.ProductDto;
import hr.abysalto.hiring.mid.product.infrastructure.persistance.entity.ProductEntity;
import hr.abysalto.hiring.mid.shared.readmodel.ProductView;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    private ProductMapper() {}

    public static ProductDto toDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getTitle(),
                product.getPrice()
        );
    }

    public static ProductEntity toEntity(Product product) {
        if (product == null) return null;
        return new ProductEntity(
                null,
                product.getTitle(),
                product.getPrice()
        );
    }

    public static Product toDomain(ProductEntity entity) {
        return new Product(entity.getId(), entity.getTitle(), entity.getPrice());
    }

    public static ProductDto toDto(ProductView view) {
        return new ProductDto(
                view.id(),
                view.title(),
                view.price()
        );
    }
}