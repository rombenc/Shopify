package com.ecommerce.shopify.service;

import com.ecommerce.shopify.model.dto.CustomerRequest;
import com.ecommerce.shopify.model.dto.RegisterResponse;

public interface AuthService {
    RegisterResponse registerCustomer(CustomerRequest request);

}
