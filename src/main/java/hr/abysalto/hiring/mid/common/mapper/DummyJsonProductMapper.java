package hr.abysalto.hiring.mid.common.mapper;

import hr.abysalto.hiring.mid.product.domain.Product;
import hr.abysalto.hiring.mid.product.dto.DummyJsonProductResponse;

public class DummyJsonProductMapper {

    private DummyJsonProductMapper() {}

    public static Product toDomain(DummyJsonProductResponse dto) {
        return new Product(
                dto.id(),
                dto.title(),
                dto.price()
        );
    }
}
