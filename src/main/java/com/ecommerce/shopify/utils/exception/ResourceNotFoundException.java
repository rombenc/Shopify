package com.ecommerce.shopify.utils.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends  RuntimeException{
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
 }

