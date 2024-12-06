package com.ecommerce.shopify.utils.specification;

import com.ecommerce.shopify.model.entity.Customer;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CustomerSpecification {
    public static Specification<Customer> getSpecification(String query) {
        return (root, cq, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.like(
                    cb.lower(root.get("name")),
                    "%" + query.toLowerCase() + "%"
            ));

            predicates.add(cb.like(
                    cb.lower(root.get("email")),
                    "%" + query.toLowerCase() + "%"
            ));

            return cb.or(
                    predicates.toArray(new Predicate[0])
            );
        };
    }
}