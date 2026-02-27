package hr.abysalto.hiring.mid;

import hr.abysalto.hiring.mid.cart.app.port.out.ProductGateway;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;


@TestConfiguration
class TestConfig {

    @Bean
    public ProductGateway productGateway() {
        return Mockito.mock(ProductGateway.class);
    }
}