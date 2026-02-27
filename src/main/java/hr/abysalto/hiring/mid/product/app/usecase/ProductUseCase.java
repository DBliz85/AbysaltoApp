package hr.abysalto.hiring.mid.product.app.usecase;

import hr.abysalto.hiring.mid.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductUseCase {
    Page<Product> getProducts(Pageable pageable);

    Product getProductById(Long id);
}