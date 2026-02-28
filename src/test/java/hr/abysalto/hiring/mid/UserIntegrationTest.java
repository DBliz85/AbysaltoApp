package hr.abysalto.hiring.mid;

import hr.abysalto.hiring.mid.cart.dto.AddToCartRequest;
import hr.abysalto.hiring.mid.cart.dto.CartDto;
import hr.abysalto.hiring.mid.favorites.dto.FavoritesDto;
import hr.abysalto.hiring.mid.product.dto.ProductDto;
import hr.abysalto.hiring.mid.user.domain.User;
import hr.abysalto.hiring.mid.user.domain.UserRepository;
import hr.abysalto.hiring.mid.user.dto.LoginResponse;
import hr.abysalto.hiring.mid.user.dto.RegisterRequest;
import hr.abysalto.hiring.mid.user.dto.UserDto;
import hr.abysalto.hiring.mid.user.infrastructure.persistence.entity.UserEntity;
import hr.abysalto.hiring.mid.user.mapper.UserMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserIntegrationTest {

    @Container
    @ServiceConnection
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0");

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private static String token;

    @BeforeAll
    static void setup() { System.setProperty("api.version", "1.44"); }

    @Test
    void shouldSaveAndRetrieveUser() {
        UserEntity user = new UserEntity();
        user.setUsername("test");
        user.setPassword("password123");
        userRepository.save(UserMapper.toDomain(user));

        Optional<User> found = userRepository.findByUsername("test");

        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("test");
    }

    @Test
    @Order(1)
    void shouldReturnCurrentUser() {

        RegisterRequest request = new RegisterRequest("john", "password123");

        // register
        restTemplate.postForEntity(
                "/v1/api/auth/register",
                request,
                Void.class
        );

        // login
        ResponseEntity<LoginResponse> loginResponse =
                restTemplate.postForEntity(
                        "/v1/api/auth/login",
                        request,
                        LoginResponse.class
                );

        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        token = loginResponse.getBody().token();

        // call /me
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<UserDto> response =
                restTemplate.exchange(
                        "/v1/api/auth/me",
                        HttpMethod.GET,
                        entity,
                        UserDto.class
                );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().username()).isEqualTo("john");
    }

    @Test
    @Order(2)
    void shouldReturnProductsPage() {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response =
                restTemplate.exchange(
                        "/v1/api/products",
                        HttpMethod.GET,
                        entity,
                        Map.class
                );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).containsKey("content");
    }

    @Test
    @Order(3)
    void shouldReturnProductById() {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<ProductDto> response =
                restTemplate.exchange(
                        "/v1/api/products/1",
                        HttpMethod.GET,
                        entity,
                        ProductDto.class
                );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    @Order(4)
    void shouldAddProductToFavorites() {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Void> response =
                restTemplate.exchange(
                        "/v1/api/favorites/1",
                        HttpMethod.POST,
                        entity,
                        Void.class
                );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @Order(5)
    void shouldReturnFavorites() {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<FavoritesDto> response =
                restTemplate.exchange(
                        "/v1/api/favorites",
                        HttpMethod.GET,
                        entity,
                        FavoritesDto.class
                );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        // assuming FavoritesDto contains a list called products
        assertThat(response.getBody().favorites()).isNotEmpty();
    }

    @Test
    @Order(6)
    void shouldRemoveProductFromFavorites() {

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<Void> response =
                restTemplate.exchange(
                        "/v1/api/favorites/1",
                        HttpMethod.DELETE,
                        entity,
                        Void.class
                );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @Order(7)
    void shouldReturnForbiddenWhenNoToken() {

        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        "/v1/api/favorites/1",
                        null,
                        String.class
                );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    @Order(8)
    void shouldAddItemToCart() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        AddToCartRequest request = new AddToCartRequest(1L, 2); // productId = 1, quantity = 2
        HttpEntity<AddToCartRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<CartDto> response =
                restTemplate.exchange("/v1/api/cart/items", HttpMethod.POST, entity, CartDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().items()).isNotEmpty();
        assertThat(response.getBody().items().get(0).quantity()).isEqualTo(2);
    }

    @Test
    @Order(9)
    void shouldReturnCart() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<CartDto> response =
                restTemplate.exchange("/v1/api/cart", HttpMethod.GET, entity, CartDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().items()).isNotEmpty();
    }

    @Test
    @Order(10)
    void shouldRemoveItemFromCart() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<CartDto> response =
                restTemplate.exchange("/v1/api/cart/items/1", HttpMethod.DELETE, entity, CartDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().items()).isEmpty();
    }
}