package hr.abysalto.hiring.mid.product.infrastructure.persistance;

import hr.abysalto.hiring.mid.common.mapper.ProductMapper;
import hr.abysalto.hiring.mid.product.domain.Product;
import hr.abysalto.hiring.mid.product.domain.ProductRepository;
import hr.abysalto.hiring.mid.product.infrastructure.persistance.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class JpaProductRepository implements ProductRepository {

    private final SpringProductJpaRepository springRepo;

    public JpaProductRepository(SpringProductJpaRepository springRepo, ProductMapper mapper) {
        this.springRepo = springRepo;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return springRepo.findById(id).map(ProductMapper::toDomain);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return springRepo.findAll(pageable).map(ProductMapper::toDomain);
    }

    @Override
    public Product save(Product product) {
        ProductEntity entity = ProductMapper.toEntity(product);
        ProductEntity saved = springRepo.save(entity);
        return ProductMapper.toDomain(saved);
    }

    @Override
    public List<Product> saveAll(List<Product> products) {
        List<ProductEntity> entities = products.stream()
                .map(ProductMapper::toEntity)
                .toList();

        return springRepo.saveAll(entities).stream()
                .map(ProductMapper::toDomain)
                .toList();
    }

    @Override
    public Collection<Product> findAllById(Set<Long> ids) {
        return springRepo.findAllById(ids).stream().map(ProductMapper::toDomain).toList();
    }

    @Override
    public boolean existsById(Long productId) {
        return springRepo.existsById(productId);
    }
}