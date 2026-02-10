package hr.abysalto.hiring.mid.user.app.out;

import hr.abysalto.hiring.mid.shared.readmodel.ProductView;

import java.util.List;
import java.util.Set;

public interface ProductGateway {
    boolean exists(Long productId);
    List<ProductView> getProducts(Set<Long> ids);
}
