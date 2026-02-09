package hr.abysalto.hiring.mid.product.web;

import hr.abysalto.hiring.mid.product.app.usecase.ProductService;
import hr.abysalto.hiring.mid.product.dto.ProductDto;
import hr.abysalto.hiring.mid.product.dto.ProductRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDto> getProducts(Pageable pageable) {
        return productService.getProducts(pageable).getContent();
    }
    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createProduct(@Valid @RequestBody ProductRequest request) {
        return productService.createProduct(request.name(), request.price());
    }
}