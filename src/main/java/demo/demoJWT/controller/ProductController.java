package demo.demoJWT.controller;

import demo.demoJWT.model.Product;
import demo.demoJWT.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;
    @PostMapping
    public Product createNew(@RequestBody Product product){
        return productRepository.save(product);
    }

    @GetMapping
    public List<Product> getAll(){
        return null;
    }

}
