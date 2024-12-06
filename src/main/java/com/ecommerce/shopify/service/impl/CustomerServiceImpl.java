package com.ecommerce.shopify.service.impl;

import com.ecommerce.shopify.model.dto.CustomerRequest;
import com.ecommerce.shopify.model.dto.CustomerResponse;
import com.ecommerce.shopify.model.entity.Customer;
import com.ecommerce.shopify.repository.CustomerRepository;
import com.ecommerce.shopify.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.ecommerce.shopify.utils.exception.ValidationException;
import com.ecommerce.shopify.utils.specification.CustomerSpecification;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponse create(CustomerRequest request) {
        Customer customer = Customer.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .build();
        try {
            customerRepository.save(customer);
        } catch (Exception e) {
            throw new ValidationException("Constrain Error", e);
        }
        return toCustomerResponse(customer);
    }

    @Override
    public List<CustomerResponse> getAll(String name) {
        return customerRepository.findAll()
                .stream()
                .map(this::toCustomerResponse)
                .toList();
    }

    @Override
    public CustomerResponse update(CustomerRequest request) {
        Customer customer = customerRepository.findById(request.getId())
                .orElseThrow(() -> new ValidationException("Customer not found", null));
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setAddress(request.getAddress());

        customerRepository.save(customer);
        return toCustomerResponse(customer);
    }

    @Override
    public void deleteById(String id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ValidationException("Customer not found", null));
        customerRepository.delete(customer);
    }

    public CustomerResponse getById(String id) {
        return customerRepository.findById(id)
                .map(this::toCustomerResponse)
                .orElseThrow(() -> new ValidationException("Customer not found", null));
    }

    public List<CustomerResponse> searchCustomers(String query) {
        Specification<Customer> spec = CustomerSpecification.getSpecification(query);
        return customerRepository.findAll(spec).stream().map(this::toCustomerResponse).toList();
    }

    private CustomerResponse toCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .address(customer.getAddress())
                .build();
    }
}
