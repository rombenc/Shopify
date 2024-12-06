package com.ecommerce.shopify.repository;

import com.ecommerce.shopify.model.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserAccount, String> {
}
