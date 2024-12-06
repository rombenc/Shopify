package com.ecommerce.shopify.model.dto;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransactionRequest {
    private String customerId;
    private List<TransactionDetailRequest> transactionDetailRequests;
}
