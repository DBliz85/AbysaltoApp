package hr.abysalto.hiring.mid.infrastructure.client;

import hr.abysalto.hiring.mid.product.dto.ProductDto;
import hr.abysalto.hiring.mid.product.dto.ProductsResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class DummyJsonClient {

    private final RestTemplate restTemplate;

    public DummyJsonClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Cacheable(value = "dummyProducts", unless = "#result == null or #result.isEmpty()")
    public List<ProductDto> fetchProducts() {
        ProductsResponse response = restTemplate.getForObject(
                "https://dummyjson.com/products",
                ProductsResponse.class
        );
        return Optional.ofNullable(response)
                .map(ProductsResponse::getProducts)
                .orElse(Collections.emptyList());
    }
}