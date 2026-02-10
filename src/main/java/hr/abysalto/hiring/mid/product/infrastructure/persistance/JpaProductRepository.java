package hr.abysalto.hiring.mid.product.infrastructure.persistance;

import hr.abysalto.hiring.mid.common.mapper.ProductMapper;
import hr.abysalto.hiring.mid.product.app.usecase.port.out.ProductPersistencePort;
import hr.abysalto.hiring.mid.product.domain.Product;
import hr.abysalto.hiring.mid.product.infrastructure.persistance.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaProductRepository implements ProductPersistencePort {

    private final SpringProductJpaRepository repository;

    public JpaProductRepository(SpringProductJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::toDomain);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id).map(ProductMapper::toDomain);
    }

    private Product toDomain(ProductEntity entity) {
        return new Product(
                entity.getId(),
                entity.getTitle(),
                entity.getPrice()
        );
    }

    private ProductEntity toEntity(Product product) {
        ProductEntity entity = new ProductEntity();
        entity.setId(product.getId());
        entity.setTitle(product.getTitle());
        entity.setPrice(product.getPrice());
        return entity;
    }
}