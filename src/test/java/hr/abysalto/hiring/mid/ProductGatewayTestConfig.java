package hr.abysalto.hiring.mid;

import hr.abysalto.hiring.mid.cart.app.port.out.ProductGateway;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

public class ProductGatewayTestConfig {
    @Bean
    public ProductGateway productGateway() {
        return mock(ProductGateway.class);
    }
}
