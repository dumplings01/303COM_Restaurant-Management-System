package com.fyp.restaurant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fyp.restaurant.model.Admin;
import com.fyp.restaurant.model.AdminRequestModel;
import com.fyp.restaurant.model.Customer;
import com.fyp.restaurant.model.CustomerRequestModel;
import com.fyp.restaurant.repository.AdminRepository;
import com.fyp.restaurant.repository.CustomerRepository;
import com.fyp.restaurant.service.SystemService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SystemService systemService;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private AdminRepository adminRepository;

    Admin adminOne;
    Admin adminTwo;

    List<Admin> adminList = new ArrayList<>();


    @BeforeEach
    void setUp() {

        List<String> roles = new ArrayList<>();
        roles.add("role_1");
        roles.add("role_2");

        adminOne = new Admin(UUID.fromString("19d9ad70-d409-46bc-93ff-1e20150742fe"),"John Doe", "johndoe@example.com",
                "password",roles);
        adminTwo = new Admin(UUID.fromString("35cc2a5e-2fc9-4e09-b5d0-88002d675fb0"),"John Two", "johnny@example.com",
                "password",roles);
        adminList.add(adminOne);
        adminList.add(adminTwo);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAdminProfile() throws Exception {

        Admin admin = adminList.get(0);
        when(adminRepository.findByAdminId(admin.getAdminId())).thenReturn(admin);

        // Perform the get request
        mockMvc.perform(get("/admin/getAdminProfile")
                        .param("adminId", admin.getAdminId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.adminId").value(admin.getAdminId().toString()))
                .andExpect(jsonPath("$.name").value(admin.getName()))
                .andExpect(jsonPath("$.email").value(admin.getEmail()))
                .andExpect(jsonPath("$.roles").value(admin.getRoles()))
                .andExpect(jsonPath("$.password").value(admin.getPassword()));
    }

    @Test
    void getAdminRole() throws Exception {
        Admin admin = adminList.get(0);
        when(adminRepository.findByEmail(admin.getEmail())).thenReturn(admin);

        // Perform the get request
        mockMvc.perform(get("/admin/getAdminRoleByEmail")
                        .param("email", admin.getEmail())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.adminId").value(admin.getAdminId().toString()))
                .andExpect(jsonPath("$.name").value(admin.getName()))
                .andExpect(jsonPath("$.email").value(admin.getEmail()))
                .andExpect(jsonPath("$.roles").value(admin.getRoles()))
                .andExpect(jsonPath("$.password").value(admin.getPassword()));

    }

    @Test
    void getAllStaffs() throws Exception {
        when(adminRepository.findAll()).thenReturn(adminList);

        mockMvc.perform(get("/admin/getAllStaffs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));

    }

    @Test
    void createNewAdmin() throws Exception {

        List<String> roles = new ArrayList<>();
        roles.add("role_1");
        roles.add("role_2");

        AdminRequestModel adminDetails = new AdminRequestModel();
        adminDetails.setName("Name Test");
        adminDetails.setEmail("joe@gmail.com");
        adminDetails.setRoles(roles);
        adminDetails.setPassword("3223dfere");

        Admin createdAdmin = new Admin();
        createdAdmin.setName(adminDetails.getName());
        createdAdmin.setEmail(adminDetails.getEmail());
        createdAdmin.setRoles(adminDetails.getRoles());
        createdAdmin.setPassword(adminDetails.getPassword());

        when(adminRepository.save(Mockito.any())).thenReturn(createdAdmin);

        mockMvc.perform(post("/admin/createNewAdmin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(adminDetails)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(createdAdmin.getName()))
                .andExpect(jsonPath("$.email").value(createdAdmin.getEmail()))
                .andExpect(jsonPath("$.roles").value(createdAdmin.getRoles()))
                .andExpect(jsonPath("$.password").exists());
    }

    @Test
    void deleteAdminProfile() throws Exception {
        UUID adminId = UUID.fromString("19d9ad70-d409-46bc-93ff-1e20150742fe");

        // Perform the DELETE request
        mockMvc.perform(delete("/admin/deleteAdminProfile")
                        .param("adminId", adminId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("Admin profile deleted successfully!"));
    }

    private String asJsonString(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}