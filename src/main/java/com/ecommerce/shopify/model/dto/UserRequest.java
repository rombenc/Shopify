package com.ecommerce.shopify.model.dto;

import com.ecommerce.shopify.model.entity.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@Builder
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

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
