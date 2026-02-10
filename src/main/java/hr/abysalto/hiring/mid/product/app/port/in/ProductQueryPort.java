package hr.abysalto.hiring.mid.product.app.port.in;

import hr.abysalto.hiring.mid.shared.readmodel.ProductView;

import java.util.List;
import java.util.Set;

public interface ProductQueryPort {
    ProductView getById(Long id);
    List<ProductView> getByIds(Set<Long> ids);
}