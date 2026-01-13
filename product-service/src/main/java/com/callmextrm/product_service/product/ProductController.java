package com.callmextrm.product_service.product;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService product) {
        this.productService = product;
    }


    //Get all products CONTROLLER
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    //Get product By Id CONTROLLER
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    //Add product CONTROLLER
    @PostMapping
    public ResponseEntity<Product> addProduct(@Valid @RequestBody ProductCreateDTO dto) {
        Product saved = productService.addProduct(dto.product(), dto.initialQuantity());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    //Delete product CONTROLLER
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    //Update product CONTROLLER


    //Discontinue products Controller
    @PatchMapping("/{id}/discontinue")
    public ResponseEntity<Product> discontinueProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.discontinueProduct(id));
    }
}