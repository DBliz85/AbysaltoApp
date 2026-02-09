package hr.abysalto.hiring.mid.product.persistance.repository;


import hr.abysalto.hiring.mid.product.domain.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringProductJpaRepository extends JpaRepository<ProductEntity, Long> {
}
