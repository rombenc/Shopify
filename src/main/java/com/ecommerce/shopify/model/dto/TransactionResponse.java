package com.ecommerce.shopify.model.dto;

import com.ecommerce.shopify.model.entity.Customer;
import com.ecommerce.shopify.model.entity.TransactionDetail;
import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransactionResponse {
    private String id;
    private Customer customer;
    private Date transactionDate;
    private List<TransactionDetail> transactionDetails;
    private Long totalPayment;
}