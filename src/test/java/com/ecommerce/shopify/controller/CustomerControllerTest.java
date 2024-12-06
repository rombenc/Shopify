package com.ecommerce.shopify.controller;

import com.ecommerce.shopify.model.dto.CommonResponse;
import com.ecommerce.shopify.model.dto.CustomerRequest;
import com.ecommerce.shopify.model.dto.CustomerResponse;
import com.ecommerce.shopify.model.entity.Customer;
import com.ecommerce.shopify.repository.CustomerRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        customerRepository.deleteAll();
    }

    @Order(1)
    @Test
    void createCustomerTest() throws Exception {
        // Create a customer request
        CustomerRequest request = new CustomerRequest();
        request.setName("Dimas");
        request.setEmail("dimas@example.com");
        request.setPhoneNumber("1234567890");
        request.setAddress("Jl. Kaki");

        String json = objectMapper.writeValueAsString(request);

        MvcResult result = mockMvc.perform(
                post("/api/v1/customers")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpectAll(
                status().isOk()
        ).andReturn();

        CommonResponse<CustomerResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

        assertNotNull(response.getData());
        assertEquals("Dimas", response.getData().getName());
        assertEquals("dimas@example.com", response.getData().getEmail());
        assertEquals("1234567890", response.getData().getPhoneNumber());
    }

    @Order(2)
    @Test
    void getCustomerByIdTest() throws Exception {
        Customer customer = new Customer();
        customer.setName("Dimas");
        customer.setEmail("dimas@example.com");
        customer.setPhoneNumber("1234567890");
        customer.setAddress("123 Main St");
        customerRepository.save(customer);

        MvcResult result = mockMvc.perform(
                get("/api/v1/customers/" + customer.getId())
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andReturn();

        CommonResponse<CustomerResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

        assertNotNull(response.getData());
        assertEquals("Dimas", response.getData().getName());
        assertEquals("dimas@example.com", response.getData().getEmail());
        assertEquals("1234567890", response.getData().getPhoneNumber());
    }

    @Order(3)
    @Test
    void getAllCustomersTest() throws Exception {
        // Create a customer
        Customer customer = new Customer();
        customer.setName("Dimas");
        customer.setEmail("dimas@example.com");
        customer.setPhoneNumber("1234567890");
        customer.setAddress("Jl. Kaki");
        customerRepository.save(customer);

        mockMvc.perform(
                get("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            List<CustomerResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
            assertNotNull(response);
            assertTrue(response.size() > 0);
        });
    }

    @Order(4)
    @Test
    void updateCustomerTest() throws Exception {
        Customer customer = new Customer();
        customer.setName("Dimas");
        customer.setEmail("dimas@example.com");
        customer.setPhoneNumber("1234567890");
        customer.setAddress("Jl. Kaki");
        customerRepository.save(customer);

        CustomerRequest request = new CustomerRequest();
        request.setName("Dimas updated");
        request.setEmail("dimasganteng@example.com");
        request.setPhoneNumber("0987654321");
        request.setAddress("Jl. Pake Kaki");

        String json = objectMapper.writeValueAsString(request);

        MvcResult result = mockMvc.perform(
                put("/api/v1/customers/" + customer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpectAll(
                status().isOk()
        ).andReturn();

        CommonResponse<CustomerResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

        assertNotNull(response.getData());
        assertEquals("Dimas updated", response.getData().getName());
        assertEquals("dimasganteng@example.com", response.getData().getEmail());
        assertEquals("0987654321", response.getData().getPhoneNumber());
    }

    @Order(5)
    @Test
    void deleteCustomerTest() throws Exception {
        // Create a customer
        Customer customer = new Customer();
        customer.setName("Dimas");
        customer.setEmail("dimas@example.com");
        customer.setPhoneNumber("1234567890");
        customer.setAddress("Jl. Kaki");
        customerRepository.save(customer);

        MvcResult result = mockMvc.perform(
                delete("/api/v1/customers/" + customer.getId())
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andReturn();

        CommonResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

        assertNotNull(response.getData());
        assertEquals("Customer Deleted", response.getData());
    }

    @Order(6)
    @Test
    void searchCustomerByNameTest() throws Exception {
        // Create a customer Dimas
        Customer customerDimas = new Customer();
        customerDimas.setName("Dimas");
        customerDimas.setEmail("dimas@example.com");
        customerDimas.setPhoneNumber("1234567890");
        customerDimas.setAddress("Jl. Kaki");
        customerRepository.save(customerDimas);

        // Create another customer Jaka
        Customer customerJaka = new Customer();
        customerJaka.setName("Jaka");
        customerJaka.setEmail("jaka@example.com");
        customerJaka.setPhoneNumber("0987654321");
        customerJaka.setAddress("Jl. Kaki");
        customerRepository.save(customerJaka);

        // Perform the search request
        MvcResult result = mockMvc.perform(
                get("/api/v1/customers/search?query=Dimas")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andReturn();

        // Get the response
        List<CustomerResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

        // Assert the response contains "Dimas"
        assertNotNull(response);
        assertTrue(response.stream().anyMatch(customer -> customer.getName().equals("Dimas")));
    }
}
