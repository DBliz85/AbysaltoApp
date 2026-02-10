package hr.abysalto.hiring.mid.product.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductRepository {
    Optional<Product> findById(Long id);
    Page<Product> findAll(Pageable pageable);
    Product save(Product product);
    List<Product> saveAll(List<Product> products);

    Collection<Product> findAllById(Set<Long> ids);
}
