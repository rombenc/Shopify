package com.ecommerce.shopify.service.impl;

import com.ecommerce.shopify.model.dto.CustomerRequest;
import com.ecommerce.shopify.model.dto.RegisterResponse;
import com.ecommerce.shopify.model.entity.Customer;
import com.ecommerce.shopify.model.entity.UserAccount;
import com.ecommerce.shopify.service.AuthService;
import com.ecommerce.shopify.service.CustomerService;
import com.ecommerce.shopify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final CustomerService customerService;

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public RegisterResponse registerCustomer(CustomerRequest request) {
        // TODO: insert user account
        UserAccount account = UserAccount.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .build();

        account = userService.create(account);

        // TODO: insert customer
        CustomerRequest request1 = CustomerRequest.builder()
                .name(request.getName())
                .address(request.getAddress())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .createAt(String.valueOf(Date.from(Instant.now())))
                .build();

        customerService.create(request1);

        return RegisterResponse.builder()
                .username(request.getUsername())
                .build();
    }
}
