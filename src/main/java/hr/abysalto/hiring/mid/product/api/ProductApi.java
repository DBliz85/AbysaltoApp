package hr.abysalto.hiring.mid.product.api;

import hr.abysalto.hiring.mid.product.domain.ProductRepository;
import hr.abysalto.hiring.mid.product.dto.ProductDto;
import org.springframework.stereotype.Component;

@Component
public class ProductApi {

    private final ProductRepository productRepository;

    public ProductApi(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDto findProductById(Long id) {
        return productRepository.findById(id)
                .map(p -> new ProductDto(p.getId(), p.getTitle(), p.getPrice()))
                .orElse(null);
    }
}
