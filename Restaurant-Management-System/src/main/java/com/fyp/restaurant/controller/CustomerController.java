package com.fyp.restaurant.controller;

import com.fyp.restaurant.model.Customer;
import com.fyp.restaurant.model.CustomerRequestModel;
import com.fyp.restaurant.repository.CustomerRepository;
import com.fyp.restaurant.service.SystemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path="/customer")
@CrossOrigin("http://localhost:3000")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // get all customer profile
    @GetMapping("/getAllCustomerProfile")
    public Page<Customer> getAllCustomerProfile(@RequestParam(defaultValue = "0") int currentPage)
    {
        Pageable pageable = PageRequest.of(currentPage, 10);
        return (Page<Customer>) this.customerRepository.findAll(pageable);
    }

    // get customer profile
    @GetMapping("/getCustomerProfile")
    public Customer getCustomerProfile (@RequestParam(name="customerId") UUID customerId)
    {
        return this.customerRepository.findByCustomerId(customerId);
    }

    // create customer profile
    @PostMapping("/createCustomerAccount")
    public ResponseEntity<?> createNewCustomer(@Valid @RequestBody CustomerRequestModel customer)
    {
        Customer value = new SystemService().createCustomerAccount(customer);

        customerRepository.save(value);

        return new ResponseEntity<Customer>(value, HttpStatus.OK);
    }

    // delete customer profile
    @DeleteMapping("/deleteCustomerProfile")
    public ResponseEntity<?> deleteCustomerProfile(@RequestParam(value="customerId") UUID customerId)
    {
        customerRepository.deleteById(customerId);
        return new ResponseEntity<>("Customer profile deleted successfully!", HttpStatus.OK);
    }

    // login (customer)
    @PostMapping("/login")
    public ResponseEntity<?> loginCustomer(@RequestBody CustomerRequestModel customer)
    {
        if (!customerRepository.existsByEmail(customer.getEmail()) || !customerRepository.existsByEmail(customer.getEmail())){
            return new ResponseEntity<>("Login unsuccessful!", HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(customer.getEmail(), HttpStatus.OK);
        }
    }
}
