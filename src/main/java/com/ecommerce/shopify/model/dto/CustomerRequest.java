package com.ecommerce.shopify.model.dto;

import com.ecommerce.shopify.model.entity.UserAccount;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    private String id;
    private String Username;
    private String password;
    @JsonAlias("full_name")
    private String name;
    private String email;
    @JsonAlias("phone_number")
    private String phoneNumber;
    private String address;
    private String createAt;
}