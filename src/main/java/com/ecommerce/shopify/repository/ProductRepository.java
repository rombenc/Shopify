package com.ecommerce.shopify.repository;

import com.ecommerce.shopify.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    // Built-in methods:
    // save(), findById(), findAll(), delete()

    // Custom query methods:
//    List<Product> findAllByNameLikeOrderByNameDesc(String name);

    // Custom query native:
//    @Query(nativeQuery = true, value = "SELECT * FROM m_product WHERE name LIKE :name ORDER BY name DESC")
//    List<Product> findProductByNameQuery(@Param("name") String name);

    // Custom query JPQL
    @Query("SELECT p FROM Product p WHERE p.name LIKE :name ORDER BY p.name DESC")
    List<Product> findProductByNameJPQL(@Param("name") String name);
}