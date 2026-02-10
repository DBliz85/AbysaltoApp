package hr.abysalto.hiring.mid.product.app.usecase.service;

import hr.abysalto.hiring.mid.common.mapper.ProductMapper;
import hr.abysalto.hiring.mid.product.app.usecase.port.in.ProductUseCase;
import hr.abysalto.hiring.mid.product.domain.Product;
import hr.abysalto.hiring.mid.product.dto.ProductDto;
import hr.abysalto.hiring.mid.product.infrastructure.persistance.client.DummyJsonClientImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements ProductUseCase {

    private final DummyJsonClientImpl dummyJsonClient;

    public ProductService(
            DummyJsonClientImpl dummyJsonClient
    ) {
        this.dummyJsonClient = dummyJsonClient;
    }

    @Override
    public Page<ProductDto> getAllProducts(Pageable pageable) {
        List<Product> products = dummyJsonClient.fetchProducts();

        List<ProductDto> dtos = products.stream()
                .map(ProductMapper::toDto)
                .toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), dtos.size());

        if (start > end) {
            return Page.empty(pageable);
        }

        return new PageImpl<>(
                dtos.subList(start, end),
                pageable,
                dtos.size()
        );
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = dummyJsonClient.fetchProductById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        return ProductMapper.toDto(product);
    }
}
