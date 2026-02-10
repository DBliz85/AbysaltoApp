package hr.abysalto.hiring.mid.product.app.usecase.port.in;

import hr.abysalto.hiring.mid.product.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductUseCase {
    Page<ProductDto> getAllProducts(Pageable pageable);
    ProductDto getProductById(Long id);
}