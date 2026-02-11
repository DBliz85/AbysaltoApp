package hr.abysalto.hiring.mid.product.infrastructure.persistance.client;

import hr.abysalto.hiring.mid.common.mapper.DummyJsonProductMapper;
import hr.abysalto.hiring.mid.product.domain.Product;
import hr.abysalto.hiring.mid.product.dto.DummyJsonProductResponse;
import hr.abysalto.hiring.mid.product.dto.ProductDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Component
public class DummyJsonClientImpl implements DummyJsonClient{

    private final RestTemplate restTemplate = new RestTemplate();

    @Cacheable(value = "products")
    public List<ProductDto> fetchProducts() {
        String url = "https://dummyjson.com/products";
        DummyResponse response = restTemplate.getForObject(url, DummyResponse.class);

        if (response == null) {
            return List.of();
        }
        return response.products.stream().toList();
    }

    @Cacheable(value = "product", key = "#id")
    @Override
    public Optional<Product> fetchProductById(Long id) {
        try {
            DummyJsonProductResponse response =
                    restTemplate.getForObject(
                            "https://dummyjson.com/products/{id}",
                            DummyJsonProductResponse.class,
                            id
                    );

            if (response == null) {
                return Optional.empty();
            }

            return Optional.of(DummyJsonProductMapper.toDomain(response));
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }

    static class DummyResponse {
        public List<ProductDto> products;
    }
}