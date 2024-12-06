package com.ecommerce.shopify.controller;

import com.ecommerce.shopify.model.dto.CommonResponse;
import com.ecommerce.shopify.model.dto.CustomerRequest;
import com.ecommerce.shopify.model.dto.CustomerResponse;
import com.ecommerce.shopify.service.impl.CustomerServiceImpl;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final Validator validator;

    private final CustomerServiceImpl customerService;

    @PostMapping
    public CommonResponse<CustomerResponse> create(@RequestBody CustomerRequest request) {
        return CommonResponse.<CustomerResponse>builder()
                .message("Created")
                .data(customerService.create(request))
                .statusCode(HttpStatus.CREATED.value())
                .build();
    }

    @GetMapping("/search")
    public List<CustomerResponse> searchCustomers(@RequestParam String query) {
        return customerService.searchCustomers(query);
    }

    @GetMapping
    public List<CustomerResponse> getAllCustomers() {
        List<CustomerResponse> customer = customerService.getAll(null);
        return ResponseEntity.ok(customer).getBody();
    }

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public CommonResponse<CustomerResponse> getById(@PathVariable String id) {
        return CommonResponse.<CustomerResponse>builder()
                .message("Found")
                .data(customerService.getById(id))
                .statusCode(HttpStatus.OK.value())
                .build();
    }

    @PutMapping("/{id}")
    public CommonResponse<CustomerResponse> update(@RequestBody CustomerRequest request, @PathVariable String id) {
        request.setId(id);
        return CommonResponse.<CustomerResponse>builder()
                .message("Updated")
                .data(customerService.update(request))
                .statusCode(HttpStatus.OK.value())
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<String>> deleteById(@PathVariable String id) {
        boolean customerExists = customerService.getById(id) != null;
        if (!customerExists) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(CommonResponse.<String>builder()
                            .message("Customer not found")
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build());
        }
        customerService.deleteById(id);
        return ResponseEntity.ok(CommonResponse.<String>builder()
                .message("Deleted")
                .statusCode(HttpStatus.OK.value())
                .build());
    }
}