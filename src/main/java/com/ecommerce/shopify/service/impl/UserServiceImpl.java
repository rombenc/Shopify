package com.ecommerce.shopify.service.impl;

import com.ecommerce.shopify.model.dto.UserRequest;
import com.ecommerce.shopify.model.dto.UserResponse;
import com.ecommerce.shopify.model.entity.UserAccount;
import com.ecommerce.shopify.repository.UserRepository;
import com.ecommerce.shopify.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public UserAccount create(UserAccount user) {
        return userRepository.save(user);
    }

    @Override
    public List<UserResponse> searchUser(String query) {
        return List.of();
    }

    @Override
    public UserResponse getById(String id) {
        return null;
    }

    @Override
    public List<UserResponse> getAll(String name) {
        return List.of();
    }

    @Override
    public UserRequest update(UserRequest request) {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }
}
