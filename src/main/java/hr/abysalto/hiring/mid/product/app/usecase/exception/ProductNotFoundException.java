package hr.abysalto.hiring.mid.product.app.usecase.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long productId) {
        super(String.valueOf(productId));
    }
}
