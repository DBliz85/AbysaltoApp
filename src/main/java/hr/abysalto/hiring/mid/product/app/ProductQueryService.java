package hr.abysalto.hiring.mid.product.app;

import hr.abysalto.hiring.mid.product.app.port.in.ProductQueryPort;
import hr.abysalto.hiring.mid.product.domain.ProductRepository;
import hr.abysalto.hiring.mid.shared.readmodel.ProductView;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProductQueryService implements ProductQueryPort {
    private final ProductRepository productRepository;

    public ProductQueryService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductView getById(Long id) {
        return productRepository.findById(id).map( product -> new ProductView(product.getId(), product.getTitle(), product.getPrice()) ).orElse(null);
    }

    @Override
    public List<ProductView> getByIds(Set<Long> ids) {
        return productRepository.findAllById(ids).stream().map( product -> new ProductView(product.getId(), product.getTitle(), product.getPrice()) ).toList();
    }
}