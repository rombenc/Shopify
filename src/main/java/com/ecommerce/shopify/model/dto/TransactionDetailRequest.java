package com.ecommerce.shopify.model.dto;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransactionDetailRequest {
    private String productId;
    private Integer quantity;
}
