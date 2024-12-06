package com.ecommerce.shopify.service;

import com.ecommerce.shopify.model.dto.CustomerRequest;
import com.ecommerce.shopify.model.dto.CustomerResponse;

import java.util.List;

public interface CustomerService {
    CustomerResponse create(CustomerRequest request);
    List<CustomerResponse> searchCustomers(String query);
    CustomerResponse getById(String id);
    List<CustomerResponse> getAll(String name);
    CustomerResponse update(CustomerRequest  request);
    void deleteById(String id);

}
