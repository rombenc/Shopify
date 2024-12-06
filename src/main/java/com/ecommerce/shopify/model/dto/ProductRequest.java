package com.ecommerce.shopify.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String id;
    @NotBlank(message = "Name is required")
    private String name;
    @Min(value = 0, message = "Price must be more than 0")
    private Double price;
    @Min(value = 0, message = "Stock must be more than 0")
    private Integer stock;
}
