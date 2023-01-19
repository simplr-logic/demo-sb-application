package com.example.demo.service;

import com.example.demo.dao.entity.Product;
import com.example.demo.dao.repository.ProductRepository;
import com.example.demo.model.ProductResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResp> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductResp> resp = new ArrayList<>();

        for (Product p : products) {
            resp.add(new ProductResp(p.getId(), p.getProductCode(), p.getProductBrand(),
                p.getProductName()));
        }
        return resp;
    }

    public ProductResp getProductByProductCode(String productCode) {

        Optional<Product> product = productRepository.findByProductCode(productCode);
        if (product.isPresent()) {
            return new ProductResp(product.get().getId(), product.get().getProductCode(),
                product.get().getProductBrand(), product.get().getProductName());
        }
        return null;
    }

    public ProductResp createNewProduct(Product request) {

        Product product = productRepository.save(
            Product.builder().productCode(request.getProductCode())
                .productBrand(request.getProductBrand()).productName(request.getProductName())
                .quantity(request.getQuantity()).price(request.getPrice())
                .createdDate(LocalDate.now()).build());

        return new ProductResp(product.getId(), product.getProductCode(), product.getProductBrand(),
            product.getProductName());
    }

    public ProductResp updateProduct(Product request) {

        Optional<Product> product = productRepository.findById(request.getId());
        if (!product.isPresent()) {
            throw new RuntimeException("Product ID Not found!");
        }

        Product p = product.get();
        p.setProductCode(request.getProductCode());
        p.setProductBrand(request.getProductBrand());
        p.setProductName(request.getProductName());
        p.setQuantity(request.getQuantity());
        p.setPrice(request.getPrice());
        p.setUpdatedDate(LocalDate.now());

        productRepository.save(p);

        return new ProductResp(p.getId(), p.getProductCode(), p.getProductBrand(),
            p.getProductName());

    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
}
