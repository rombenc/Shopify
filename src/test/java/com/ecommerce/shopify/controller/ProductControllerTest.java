package com.ecommerce.shopify.controller;

import com.ecommerce.shopify.model.dto.CommonResponse;
import com.ecommerce.shopify.model.dto.ProductRequest;
import com.ecommerce.shopify.model.dto.ProductResponse;
import com.ecommerce.shopify.model.entity.Product;
import com.ecommerce.shopify.repository.ProductRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        productRepository.deleteAll();
    }

    @Order(1)
    @Test
    void createProductTest() throws Exception {
        // Create a product request
        ProductRequest request = new ProductRequest();
        request.setName("Test Product");
        request.setPrice(11.0);
        request.setStock(10);

        // Convert the request to JSON
        String json = objectMapper.writeValueAsString(request);

        // Perform the POST request
        MvcResult result = mockMvc.perform(
                post("/api/v1/product")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpectAll(
                status().isCreated()
        ).andReturn();

        // Get the response
        CommonResponse<ProductResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });

        // Assert the response
        assertNotNull(response.getData());
        assertEquals("Test Product", response.getData().getName());
        assertEquals(11.0, response.getData().getPrice());
        assertEquals(10, response.getData().getStock());
    }

    @Order(2)
    @Test
    void getProductByIdTest() throws Exception {
        // Create a product
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(11.0);
        product.setStock(10);
        productRepository.save(product);

        // Perform the GET request
        MvcResult result = mockMvc.perform(
                get("/api/v1/product/" + product.getId())
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andReturn();

        // Get the response
        CommonResponse<ProductResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });

        // Assert the response
        assertNotNull(response.getData());
        assertEquals("Test Product", response.getData().getName());
        assertEquals(11.0, response.getData().getPrice());
        assertEquals(10, response.getData().getStock());
    }

    @Order(3)
    @Test
    void getAllProduct() throws Exception {
        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setName("Test Product");
        product.setPrice(11.0);
        product.setStock(10);
        productRepository.save(product);

        mockMvc.perform(
                get("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            List<ProductResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });
            assertNotNull(response);
        });
    }

    @Order(4)
    @Test
    void updateProductTest() throws Exception {
        // Create a product
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(11.0);
        product.setStock(10);
        productRepository.save(product);

        // Create a product request
        ProductRequest request = new ProductRequest();
        request.setName("Updated Product");
        request.setPrice(12.0);
        request.setStock(20);

        // Convert the request to JSON
        String json = objectMapper.writeValueAsString(request);

        // Perform the PUT request
        MvcResult result = mockMvc.perform(
                put("/api/v1/product/" + product.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpectAll(
                status().isOk()
        ).andReturn();

        // Get the response
        CommonResponse<ProductResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });

        // Assert the response
        assertNotNull(response.getData());
        assertEquals("Updated Product", response.getData().getName());
        assertEquals(12.0, response.getData().getPrice());
        assertEquals(20, response.getData().getStock());
    }

    @Order(5)
    @Test
    void deleteProductTest() throws Exception {
        // Create a product
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(11.0);
        product.setStock(10);
        productRepository.save(product);

        // Perform the DELETE request
        MvcResult result = mockMvc.perform(
                delete("/api/v1/product/" + product.getId())
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andReturn();

        // Get the response
        CommonResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
        });

        // Assert the response
        assertNotNull(response.getData());
        assertEquals("Product ID: " + product.getId() + " has been deleted", response.getData());
    }

}
