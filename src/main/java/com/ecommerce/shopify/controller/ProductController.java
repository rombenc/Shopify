package com.ecommerce.shopify.controller;

import com.ecommerce.shopify.model.dto.CommonResponse;
import com.ecommerce.shopify.model.dto.ProductRequest;
import com.ecommerce.shopify.model.dto.ProductResponse;
import com.ecommerce.shopify.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final Validator validator;

    @PostMapping
    public ResponseEntity<CommonResponse<ProductResponse>> create(@RequestBody ProductRequest request) {
        validator.validate(request);
        ProductResponse productResponse = productService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(CommonResponse.<ProductResponse>builder().data(productResponse).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<ProductResponse>> getById(@PathVariable String id) {
        ProductResponse productResponse = productService.getById(id);
        return ResponseEntity.ok(CommonResponse.<ProductResponse>builder().data(productResponse).build());
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts(@RequestParam(required = false) String name) {
        List<ProductResponse> products = productService.getAll(name);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<ProductResponse>> update(@PathVariable String id, @RequestBody ProductRequest request) {
        validator.validate(request);
        ProductResponse productResponse = productService.update(request, id);
        return ResponseEntity.ok(CommonResponse.<ProductResponse>builder().data(productResponse).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<String>> deleteById(@PathVariable String id) {
        productService.deleteById(id);
        return ResponseEntity.ok(CommonResponse.<String>builder().data("Product ID: " + id + " has been deleted").build());
    }
}
