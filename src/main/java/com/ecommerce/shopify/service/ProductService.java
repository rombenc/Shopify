package com.ecommerce.shopify.service;

import com.ecommerce.shopify.model.dto.ProductRequest;
import com.ecommerce.shopify.model.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse create(ProductRequest request);
    ProductResponse getById(String id);
    List<ProductResponse> getAll(String name);
    ProductResponse update(ProductRequest request, String id);
    void deleteById(String id);
}