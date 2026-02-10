package hr.abysalto.hiring.mid.product.app.usecase.port.out;

import hr.abysalto.hiring.mid.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductPersistencePort {
    Page<Product> findAll(Pageable pageable);
    Optional<Product> findById(Long productId);
}