package com.ecommerce.shopify.repository;

import com.ecommerce.shopify.model.entity.Customer;
import com.ecommerce.shopify.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, String>, JpaSpecificationExecutor<Customer> {
    @Query("SELECT t FROM Transaction t WHERE t.customer.id = :customerId")
    List<Transaction> findByCustomerId(
            @Param("customerId") String customerId
    );

    @Query(
            value = "UPDATE customer " +
                    "SET status = :status " +
                    "WHERE id = :id",
            nativeQuery = true
    )
    void updateStatus(String id, Boolean status);

}