package hr.abysalto.hiring.mid.product.app.usecase.exception.dto;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long message) {
        super(String.valueOf(message));
    }
}
