package com.ecommerce.shopify.model.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user_account")
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
//    @Pattern(regexp = "^(?=.*[a-zA-Z0-9])(?=.*[!@#$%^&*_=+-]).{8,}$", message = "Password must contain number, symbol, and number")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> role;
}
