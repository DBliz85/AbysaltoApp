package hr.abysalto.hiring.mid.user.infrastructure.product;

import hr.abysalto.hiring.mid.product.app.port.in.ProductQueryPort;
import hr.abysalto.hiring.mid.shared.readmodel.ProductView;
import hr.abysalto.hiring.mid.user.app.port.out.ProductGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProductApiGateway implements ProductGateway {
    private final ProductQueryPort productQuery;

    public ProductApiGateway(ProductQueryPort productQuery) {
        this.productQuery = productQuery;
    }

    @Override
    public boolean exists(Long productId) {
        return productQuery.getById(productId) != null;
    }

    @Override
    public List<ProductView> getProducts(Set<Long> ids) {
        return productQuery.getByIds(ids);
    }
}