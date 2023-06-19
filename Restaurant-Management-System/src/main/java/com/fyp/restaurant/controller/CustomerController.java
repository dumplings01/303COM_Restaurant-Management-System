package com.fyp.restaurant.controller;

import com.fyp.restaurant.model.Customer;
import com.fyp.restaurant.model.CustomerRequestModel;
import com.fyp.restaurant.repository.AdminRepository;
import com.fyp.restaurant.repository.CustomerRepository;
import com.fyp.restaurant.service.SystemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path="/customer")
@CrossOrigin("http://localhost:3000")
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final AdminRepository adminRepository;

    public CustomerController(CustomerRepository customerRepository, AdminRepository adminRepository) {
        this.customerRepository = customerRepository;
        this.adminRepository = adminRepository;
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
    public Customer getCustomerProfile (@RequestParam(name="email") String email)
    {
        return this.customerRepository.findByEmail(email);
    }

    // create customer profile
    @PostMapping("/createCustomerAccount")
    public ResponseEntity<?> createNewCustomer(@Valid @RequestBody CustomerRequestModel customer)
    {
        Customer value = new SystemService().createCustomerAccount(customer);

        if (customerRepository.existsByEmail(value.getEmail()) || adminRepository.existsByEmail(value.getEmail())){
            return new ResponseEntity<>("Email already taken!", HttpStatus.BAD_REQUEST);
        }

        if (customerRepository.existsByContactNumber(value.getContactNumber())){
            return new ResponseEntity<>("Contact number already taken!", HttpStatus.BAD_REQUEST);
        }

        customerRepository.save(value);

        return new ResponseEntity<Customer>(value, HttpStatus.OK);
    }

    // update customer profile
    @PutMapping("/updateCustomerProfile")
    public ResponseEntity<?> updateCustomerProfile(@Valid @RequestParam(value="email") String email,
                                                   @RequestBody CustomerRequestModel customerDetails)
    {
        Customer updateProfile = customerRepository.findByEmail(email);

        if (customerRepository.existsByEmail(updateProfile.getEmail()))
        {
            return new ResponseEntity<>("Email is not available or taken! Please check again!", HttpStatus.BAD_REQUEST);
        }

        if (customerRepository.existsByContactNumber(updateProfile.getContactNumber()))
        {
            return new ResponseEntity<>("Contact number is not available or taken! Please check again!", HttpStatus.BAD_REQUEST);
        }

        updateProfile.setName(customerDetails.getName());
        updateProfile.setEmail(customerDetails.getEmail());
        updateProfile.setContactNumber(customerDetails.getContactNumber());
        updateProfile.setPassword(customerDetails.getPassword());

        customerRepository.save(updateProfile);

        return new ResponseEntity<Customer>(updateProfile, HttpStatus.OK);
    }

    // delete customer profile
    @DeleteMapping("/deleteCustomerProfile")
    public ResponseEntity<?> deleteCustomerProfile(@RequestParam(value="customerId") UUID customerId)
    {
        customerRepository.deleteById(customerId);
        return new ResponseEntity<>("Customer profile deleted successfully!", HttpStatus.OK);
    }
}
