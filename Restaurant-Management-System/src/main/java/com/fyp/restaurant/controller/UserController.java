package com.fyp.restaurant.controller;

import com.fyp.restaurant.model.Admin;
import com.fyp.restaurant.model.Customer;
import com.fyp.restaurant.model.UserRequestModel;
import com.fyp.restaurant.repository.AdminRepository;
import com.fyp.restaurant.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path="/user")
@CrossOrigin("http://localhost:3000")
public class UserController {

    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;

    public UserController(AdminRepository adminRepository, CustomerRepository customerRepository) {
        this.adminRepository = adminRepository;
        this.customerRepository = customerRepository;
    }

    BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequestModel user)
    {

        Optional<Admin> opAdmin = Optional.ofNullable(adminRepository.findByEmail(user.getEmail()));
        Optional<Customer> opCustomer = Optional.ofNullable(customerRepository.findByEmail(user.getEmail()));

        if (opAdmin.isPresent()){

            Admin dbAdmin = opAdmin.get();

            if (bcrypt.matches(user.getPassword(), dbAdmin.getPassword())){
                return new ResponseEntity<>("admin," + user.getEmail(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Incorrect password!", HttpStatus.BAD_REQUEST);
            }

        } else if (opCustomer.isPresent()){

            Customer dbCustomer = opCustomer.get();

            if  (bcrypt.matches(user.getPassword(), dbCustomer.getPassword())){
                return new ResponseEntity<>("customer," + user.getEmail(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Incorrect password!", HttpStatus.BAD_REQUEST);
            }

        } else {
            return new ResponseEntity<>("Login failed!", HttpStatus.BAD_REQUEST);
        }
    }
}
