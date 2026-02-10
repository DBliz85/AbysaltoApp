package hr.abysalto.hiring.mid.product.app.usecase.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long message) {
        super(String.valueOf(message));
    }
}
