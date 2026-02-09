package hr.abysalto.hiring.mid.product.infrastructure.persistance;


import hr.abysalto.hiring.mid.product.infrastructure.persistance.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringProductJpaRepository extends JpaRepository<ProductEntity, Long> {
}
