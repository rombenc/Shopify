package com.ecommerce.shopify.service.impl;

import com.ecommerce.shopify.utils.exception.ResourceNotFoundException;
import com.ecommerce.shopify.utils.exception.ValidationException;
import com.ecommerce.shopify.model.dto.ProductRequest;
import com.ecommerce.shopify.model.dto.ProductResponse;
import com.ecommerce.shopify.model.entity.Product;
import com.ecommerce.shopify.repository.ProductRepository;
import com.ecommerce.shopify.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public ProductResponse create(ProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .stock(request.getStock())
                .build();

        try {
            productRepository.save(product);
        } catch (Exception e) {
            throw new ValidationException("Constrain Error", e);
        }

        return toProductResponse(product);
    }

    @Override
    public ProductResponse getById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found", null));
        return toProductResponse(product);
    }

    @Override
    public List<ProductResponse> getAll(String name) {
        if (name != null) {
            return productRepository.findProductByNameJPQL("%" + name + "%").stream().map(this::toProductResponse).toList();
        }

        List<ProductResponse> products = productRepository.findAll().stream().map(this::toProductResponse).toList();

        if (products.isEmpty()) {
            throw new ResourceNotFoundException("Product not found!", null);
        }

        return products;
    }

    @Override
    public ProductResponse update(ProductRequest request, String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found", null));

        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        productRepository.save(product);

        return toProductResponse(product);
    }

    @Override
    public void deleteById(String id) {
        productRepository.deleteById(id);
    }

    private ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }
}
