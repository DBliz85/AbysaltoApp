package hr.abysalto.hiring.mid.infrastructure.persistance.repository;

import hr.abysalto.hiring.mid.domain.model.Product;
import hr.abysalto.hiring.mid.domain.repository.ProductRepository;
import hr.abysalto.hiring.mid.infrastructure.persistance.mapper.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaProductRepository implements ProductRepository {

    private final SpringProductJpaRepository springRepo;
    private final ProductMapper mapper;

    public JpaProductRepository(SpringProductJpaRepository springRepo, ProductMapper mapper) {
        this.springRepo = springRepo;
        this.mapper = mapper;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return springRepo.findById(id).map(mapper::toDomain);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return springRepo.findAll(pageable).map(mapper::toDomain);
    }

    @Override
    public Product save(Product product) {
        return mapper.toDomain(springRepo.save(mapper.toEntity(product)));
    }
}