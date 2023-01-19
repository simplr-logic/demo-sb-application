package com.example.demo.controller;

import com.example.demo.dao.entity.Product;
import com.example.demo.model.ProductResp;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/public/hello")
    public String hello() {
        log.debug("Enter hello!");
        return "Hello world!";
    }

    @GetMapping("/public/products")
    public ResponseEntity<List<ProductResp>> getAllProducts() {
        return ResponseEntity.ok().body(productService.getAllProducts());
    }

    @GetMapping("/public/product/{productCode}")
    public ResponseEntity<ProductResp> getProductByProductCode(
        @PathVariable("productCode") @NotBlank String productCode) {
        return ResponseEntity.ok().body(productService.getProductByProductCode(productCode));
    }

    @PostMapping("/secured/product")
    public ResponseEntity<ProductResp> createProduct(@RequestBody @Valid Product product) {
        return ResponseEntity.ok().body(productService.createNewProduct(product));
    }

    @PatchMapping("/secured/product/{id}")
    public ResponseEntity<ProductResp> updateProduct(@RequestBody @Valid Product product,
        @PathVariable("id") @NotBlank int id) {
        product.setId(id);
        return ResponseEntity.ok().body(productService.updateProduct(product));
    }

    @DeleteMapping("/secured/product/{id}")
    public void deleteProduct(@PathVariable("id") @NotBlank int id) {
        productService.deleteProduct(id);
    }

}
