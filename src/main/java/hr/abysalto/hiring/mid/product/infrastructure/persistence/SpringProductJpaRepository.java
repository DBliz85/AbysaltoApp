package hr.abysalto.hiring.mid.product.infrastructure.persistence;


import hr.abysalto.hiring.mid.product.infrastructure.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringProductJpaRepository extends JpaRepository<ProductEntity, Long> {
}
