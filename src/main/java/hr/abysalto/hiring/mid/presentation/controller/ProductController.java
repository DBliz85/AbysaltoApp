package hr.abysalto.hiring.mid.presentation.controller;

import hr.abysalto.hiring.mid.app.service.ProductService;
import hr.abysalto.hiring.mid.domain.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    public ProductController(ProductService productService) { this.productService = productService; }

    @GetMapping
    public List<Product> getProducts(Pageable pageable) {
        return productService.getProducts(pageable).getContent();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }
}