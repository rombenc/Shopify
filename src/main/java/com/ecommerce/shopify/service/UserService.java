package com.ecommerce.shopify.service;

import com.ecommerce.shopify.model.dto.UserRequest;
import com.ecommerce.shopify.model.dto.UserResponse;
import com.ecommerce.shopify.model.entity.UserAccount;

import java.util.List;

public interface UserService {

    UserAccount create(UserAccount user);
    List<UserResponse> searchUser(String query);
    UserResponse getById(String id);
    List<UserResponse> getAll(String name);
    UserRequest update(UserRequest  request);
    void deleteById(String id);
}
