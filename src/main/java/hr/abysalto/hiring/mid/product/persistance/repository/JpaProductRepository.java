package hr.abysalto.hiring.mid.product.persistance.repository;

import hr.abysalto.hiring.mid.product.domain.Product;
import hr.abysalto.hiring.mid.product.domain.ProductEntity;
import hr.abysalto.hiring.mid.common.mapper.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    public List<ProductEntity> saveAll(List<Product> products) {
        List<ProductEntity> entities = products.stream()
                .map(dto -> {
                    ProductEntity entity = new ProductEntity();
                    entity.setId(null);
                    entity.setName(dto.getName());
                    entity.setPrice(dto.getPrice());
                    return entity;
                })
                .toList();

        return springRepo.saveAll(entities);
    }
}