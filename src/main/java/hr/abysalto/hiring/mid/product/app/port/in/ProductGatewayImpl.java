package hr.abysalto.hiring.mid.product.app.port.in;

import hr.abysalto.hiring.mid.cart.app.usecase.port.out.ProductGateway;
import hr.abysalto.hiring.mid.product.domain.ProductRepository;
import hr.abysalto.hiring.mid.shared.readmodel.ProductView;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class ProductGatewayImpl implements ProductGateway {

    private final ProductRepository productRepository;

    public ProductGatewayImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public boolean exists(Long productId) {
        return productRepository.existsById(productId);
    }

    @Override
    public List<ProductView> getProducts(Set<Long> ids) {
        return productRepository.findAllById(ids)
                .stream()
                .map(product -> new ProductView(
                        product.getId(),
                        product.getTitle(),
                        product.getPrice()
                ))
                .toList();
    }
}
