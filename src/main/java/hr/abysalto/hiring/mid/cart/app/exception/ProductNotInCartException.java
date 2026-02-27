package hr.abysalto.hiring.mid.cart.app.exception;

public class ProductNotInCartException extends RuntimeException {
  public ProductNotInCartException(Long productId) {
    super(String.valueOf(productId));
  }
}
