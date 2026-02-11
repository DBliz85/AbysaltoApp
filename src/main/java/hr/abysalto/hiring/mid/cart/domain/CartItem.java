package hr.abysalto.hiring.mid.cart.domain;

public class CartItem {

    private Long productId;
    private int quantity;

    public CartItem(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public void increaseQuantity(int quantity) {
        this.quantity += quantity;
    }

    public Long getProductId() { return productId; }
    public int getQuantity() { return quantity; }
}
