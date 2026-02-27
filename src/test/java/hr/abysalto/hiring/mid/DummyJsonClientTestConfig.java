package hr.abysalto.hiring.mid;

import hr.abysalto.hiring.mid.product.infrastructure.persistence.client.DummyJsonClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

@TestConfiguration
class DummyJsonClientTestConfig {
    @Bean
    public DummyJsonClient dummyJsonClient() {
        return mock(DummyJsonClient.class);
    }
}