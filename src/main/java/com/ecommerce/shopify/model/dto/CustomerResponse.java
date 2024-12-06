package com.ecommerce.shopify.model.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerResponse {
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String createAt;
}
