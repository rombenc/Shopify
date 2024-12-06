package com.ecommerce.shopify.service;

import com.ecommerce.shopify.model.dto.TransactionRequest;
import com.ecommerce.shopify.model.dto.TransactionResponse;

public interface TransactionService {

    TransactionResponse create(TransactionRequest request);

}
