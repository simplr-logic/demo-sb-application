package com.example.demo.controller;

import com.example.demo.model.ProductResp;
import com.example.demo.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @BeforeEach
    void setup() {
        ProductResp mockProductResp = new ProductResp(1, "code", "brand", "name");
        ProductResp mockProductResp2 = new ProductResp(2, "code2", "brand2", "name2");
        when(productService.getAllProducts()).thenReturn(
            Arrays.asList(mockProductResp, mockProductResp2));
        when(productService.getProductByProductCode(Mockito.anyString())).thenReturn(
            mockProductResp);
        when(productService.createNewProduct(Mockito.any())).thenReturn(mockProductResp);
        when(productService.updateProduct(Mockito.any())).thenReturn(mockProductResp);
    }

    @Test
    void hello() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/public/hello").accept(MediaType.APPLICATION_JSON))
            .andDo(print()).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Get all products controller with correct data")
    void getAllProducts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/public/products"))
            .andExpect(status().isOk()).andDo(print())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(content().json(
                "[{\"id\":1,\"productCode\":\"code\",\"productBrand\":\"brand\",\"productName\":\"name\"},{\"id\":2,\"productCode\":\"code2\",\"productBrand\":\"brand2\",\"productName\":\"name2\"}]"));
    }

    @Test
    @DisplayName("Get by product code with correct data")
    void getProductByProductCode() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/public/product/{id}", 1)).andDo(print())
            .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.productCode").value("code"));
    }

    @Test
    @DisplayName("Successfully create new Product")
    void createProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/secured/product")
                .contentType(MediaType.APPLICATION_JSON).content(
                    "{\"productCode\":\"code001\",\"productBrand\":\"Sammie\", \"productName\":\"A52s\", \"quatity\":1, \"price\":1000}"))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @DisplayName("Successfully update Product")
    void updateProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/secured/product/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON).content(
                    "{\"productCode\":\"code001\",\"productBrand\":\"Sammie\", \"productName\":\"A52s\", \"quatity\":1, \"price\":1000}"))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @DisplayName("Successully delete Product")
    void deleteProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/secured/product/{id}", 1))
            .andDo(print()).andExpect(status().isOk());
    }
}