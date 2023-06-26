package com.fyp.restaurant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fyp.restaurant.model.Admin;
import com.fyp.restaurant.model.Customer;
import com.fyp.restaurant.model.UserRequestModel;
import com.fyp.restaurant.repository.AdminRepository;
import com.fyp.restaurant.repository.CustomerRepository;
import com.fyp.restaurant.service.SystemService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminRepository adminRepository;

    @MockBean
    private CustomerRepository customerRepository;

    BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

    @Test
    public void testLogin_Admin_Success() throws Exception {
        String email = "admin@example.com";
        String password = "admin123";

        UserRequestModel user = new UserRequestModel();
        user.setEmail(email);
        user.setPassword(password);

        Admin admin = new Admin();
        admin.setEmail(email);
        admin.setPassword(bcrypt.encode(password));

        when(adminRepository.findByEmail(email)).thenReturn(admin);
        when(customerRepository.findByEmail(anyString())).thenReturn(null);

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().isOk())
                .andExpect(content().string("admin," + email));

    }

    @Test
    public void testLogin_Admin_IncorrectPassword() throws Exception {
        String email = "admin@example.com";
        String password = "admin123";

        UserRequestModel user = new UserRequestModel();
        user.setEmail(email);
        user.setPassword(password);

        Admin admin = new Admin();
        admin.setEmail(email);
        admin.setPassword(bcrypt.encode("wrongpassword"));


        when(adminRepository.findByEmail(email)).thenReturn(admin);
        when(customerRepository.findByEmail(anyString())).thenReturn(null);

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Incorrect password!"));

    }

    @Test
    public void testLogin_Customer_Success() throws Exception {
        String email = "customer@example.com";
        String password = "customer123";

        UserRequestModel user = new UserRequestModel();
        user.setEmail(email);
        user.setPassword(password);

        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(bcrypt.encode(password));

        when(customerRepository.findByEmail(email)).thenReturn(customer);
        when(adminRepository.findByEmail(anyString())).thenReturn(null);

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().isOk())
                .andExpect(content().string("customer," + email));

    }

    @Test
    public void testLogin_Customer_IncorrectPassword() throws Exception {
        String email = "customer@example.com";
        String password = "customer123";

        UserRequestModel user = new UserRequestModel();
        user.setEmail(email);
        user.setPassword(password);

        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(bcrypt.encode("wrongpassword"));

        when(customerRepository.findByEmail(email)).thenReturn(customer);
        when(adminRepository.findByEmail(anyString())).thenReturn(null);

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Incorrect password!"));

    }

    @Test
    public void testLogin_LoginFailed() throws Exception {
        String email = "nonexistent@example.com";
        String password = "password";

        UserRequestModel user = new UserRequestModel();
        user.setEmail(email);
        user.setPassword(password);

        when(adminRepository.findByEmail(email)).thenReturn(null);
        when(customerRepository.findByEmail(email)).thenReturn(null);

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Login failed!"));

    }

    // Utility method to convert object to JSON string
    private static String asJsonString(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}