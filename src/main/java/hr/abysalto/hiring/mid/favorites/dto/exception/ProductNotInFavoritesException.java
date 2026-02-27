package hr.abysalto.hiring.mid.favorites.dto.exception;

public class ProductNotInFavoritesException extends RuntimeException {
  private final Long productId;

  public ProductNotInFavoritesException(Long productId) {
    super("Product with ID " + productId + " is not in the user's favorites");
    this.productId = productId;
  }

  public Long getProductId() {
    return productId;
  }
}
