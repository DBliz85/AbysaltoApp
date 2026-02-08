package hr.abysalto.hiring.mid.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CartItem {
    private final Product product;
    private int quantity;

    public void increase(int q) { this.quantity += q; }

    public BigDecimal totalPrice() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
