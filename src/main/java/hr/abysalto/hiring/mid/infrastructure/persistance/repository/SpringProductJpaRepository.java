package hr.abysalto.hiring.mid.infrastructure.persistance.repository;


import hr.abysalto.hiring.mid.infrastructure.persistance.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringProductJpaRepository extends JpaRepository<ProductEntity, Long> {
}
