package hr.abysalto.hiring.mid.product.infrastructure.persistance;

import hr.abysalto.hiring.mid.product.domain.Product;
import hr.abysalto.hiring.mid.product.infrastructure.persistance.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(Long id);
    Page<Product> findAll(Pageable pageable);
    Product save(Product product);

    List<ProductEntity> saveAll(List<Product> fetchedProducts);
}
