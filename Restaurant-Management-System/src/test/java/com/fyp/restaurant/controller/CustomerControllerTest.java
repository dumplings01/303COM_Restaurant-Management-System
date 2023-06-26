package com.fyp.restaurant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fyp.restaurant.model.Customer;
import com.fyp.restaurant.model.CustomerRequestModel;
import com.fyp.restaurant.repository.AdminRepository;
import com.fyp.restaurant.repository.CustomerRepository;
import com.fyp.restaurant.repository.ReservationRepository;
import com.fyp.restaurant.service.SystemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SystemService systemService;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private AdminRepository adminRepository;

    @MockBean
    private ReservationRepository reservationRepository;

    Customer customerOne;
    Customer customerTwo;

    List<Customer> customerList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        customerOne = new Customer(UUID.fromString("19d9ad70-d409-46bc-93ff-1e20150742fe"),"John Doe", "johndoe@example.com",
                "password","1234567890", 200);
        customerTwo = new Customer(UUID.fromString("35cc2a5e-2fc9-4e09-b5d0-88002d675fb0"),"John Two", "johnny@example.com",
                "password","3233567890", 200);
        customerList.add(customerOne);
        customerList.add(customerTwo);
    }

    @Test
    void getCustomerProfile() throws Exception{

        Customer customer = customerList.get(0);
        when(customerRepository.findByEmail(customer.getEmail())).thenReturn(customer);

        // Perform the get request
        mockMvc.perform(get("/customer/getCustomerProfile")
                        .param("email", customer.getEmail())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.customerId").value(customer.getCustomerId().toString()))
                .andExpect(jsonPath("$.name").value(customer.getName()))
                .andExpect(jsonPath("$.email").value(customer.getEmail()))
                .andExpect(jsonPath("$.password").value(customer.getPassword()))
                .andExpect(jsonPath("$.contactNumber").value(customer.getContactNumber()))
                .andExpect(jsonPath("$.loyaltyPoints").value(customer.getLoyaltyPoints()));
    }

    @Test
    void getCustomerProfileByContactNumber() throws Exception {
        Customer customer = customerList.get(0);
        when(customerRepository.findByContactNumber(customer.getContactNumber())).thenReturn(customer);

        // Perform the get request
        mockMvc.perform(get("/customer/getCustomerProfileByContactNumber")
                        .param("contactNumber", customer.getContactNumber())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.customerId").value(customer.getCustomerId().toString()))
                .andExpect(jsonPath("$.name").value(customer.getName()))
                .andExpect(jsonPath("$.email").value(customer.getEmail()))
                .andExpect(jsonPath("$.password").value(customer.getPassword()))
                .andExpect(jsonPath("$.contactNumber").value(customer.getContactNumber()))
                .andExpect(jsonPath("$.loyaltyPoints").value(customer.getLoyaltyPoints()));
    }

    @Test
    void createNewCustomer() throws Exception {
        CustomerRequestModel customerDetails = new CustomerRequestModel();
        customerDetails.setName("Name Test");
        customerDetails.setEmail("joe@gmail.com");
        customerDetails.setContactNumber("013-3232323");
        customerDetails.setPassword("3223dfere");

        Customer createdCustomer = new Customer();
        createdCustomer.setName(customerDetails.getName());
        createdCustomer.setEmail(customerDetails.getEmail());
        createdCustomer.setContactNumber(customerDetails.getContactNumber());
        createdCustomer.setPassword(customerDetails.getPassword());

        when(customerRepository.save(Mockito.any())).thenReturn(createdCustomer);

        mockMvc.perform(post("/customer/createCustomerAccount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerDetails)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(createdCustomer.getName()))
                .andExpect(jsonPath("$.email").value(createdCustomer.getEmail()))
                .andExpect(jsonPath("$.contactNumber").value(createdCustomer.getContactNumber()))
                .andExpect(jsonPath("$.password").exists());
    }

//    @Test
//    void updateProfile() throws Exception {
//
//        UUID customerId = UUID.fromString("19d9ad70-d409-46bc-93ff-1e20150742fe");
//        BCryptPasswordEncoder bcrypt = Mockito.mock(BCryptPasswordEncoder.class);
//
//        CustomerRequestModel customerRequestModel=new CustomerRequestModel();
//        customerRequestModel.setName("Lee");
//        customerRequestModel.setEmail("yy@gmail.com");
//        customerRequestModel.setContactNumber("012-4213213");
//        customerRequestModel.setPassword(bcrypt.encode("ydyfnwe"));
//
//        Customer existingCustomer = new Customer();
//        existingCustomer.setCustomerId(customerId);
//        existingCustomer.setName("Updated name");
//        existingCustomer.setEmail("yy@gmail.com");
//        existingCustomer.setContactNumber("012-7575456");
//        existingCustomer.setPassword("ydyfnwe");
//
//        Customer updatedCustomer = new Customer();
//        updatedCustomer.setCustomerId(existingCustomer.getCustomerId());
//        updatedCustomer.setName(existingCustomer.getName());
//        updatedCustomer.setEmail(existingCustomer.getEmail());
//        updatedCustomer.setContactNumber(existingCustomer.getContactNumber());
//        updatedCustomer.setPassword(existingCustomer.getPassword());
//
//        when(bcrypt.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
//        when(customerRepository.findByCustomerId(customerId)).thenReturn(existingCustomer);
//        when(customerRepository.save(Mockito.any())).thenReturn(updatedCustomer);
//
//        mockMvc.perform(put("/customer/updateCustomerProfile?customerId={customerId}", customerId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(customerRequestModel)))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.name").value(updatedCustomer.getName()))
//                .andExpect(jsonPath("$.email").value(updatedCustomer.getEmail()))
//                .andExpect(jsonPath("$.contactNumber").value(updatedCustomer.getContactNumber()))
//                .andExpect(jsonPath("$.password").exists());
//    }

//    @Test
//    void deleteCustomerProfile() throws Exception {
//        UUID customerId = UUID.fromString("19d9ad70-d409-46bc-93ff-1e20150742fe");
//
//        // Perform the DELETE request
//        mockMvc.perform(delete("/customer/deleteCustomerProfile")
//                        .param("customerId", customerId.toString()))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Customer profile deleted successfully!"));
//    }

    private String asJsonString(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}