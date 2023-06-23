package com.fyp.restaurant.controller;

import com.fyp.restaurant.model.Customer;
import com.fyp.restaurant.model.CustomerRequestModel;
import com.fyp.restaurant.model.LoyaltyProgramRequestModel;
import com.fyp.restaurant.model.Reservation;
import com.fyp.restaurant.repository.AdminRepository;
import com.fyp.restaurant.repository.CustomerRepository;
import com.fyp.restaurant.repository.ReservationRepository;
import com.fyp.restaurant.service.ReservationService;
import com.fyp.restaurant.service.SystemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path="/customer")
@CrossOrigin("http://localhost:3000")
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final AdminRepository adminRepository;

    private final ReservationRepository reservationRepository;

    public CustomerController(CustomerRepository customerRepository, AdminRepository adminRepository, ReservationRepository reservationRepository) {
        this.customerRepository = customerRepository;
        this.adminRepository = adminRepository;
        this.reservationRepository = reservationRepository;
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

    // get customer profile
    @GetMapping("/getCustomerProfileByContactNumber")
    public Customer getCustomerProfileByContactNumber (@RequestParam(name="contactNumber") String contactNumber)
    {
        return this.customerRepository.findByContactNumber(contactNumber);
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
    public ResponseEntity<?> updateProfile(@Valid @RequestParam(value="customerId") UUID customerId,
                                                   @RequestBody CustomerRequestModel customerDetails)
    {

        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

        Customer updateProfile = customerRepository.findByCustomerId(customerId);

        // checks if contact number is taken (other than current contact number)
        if (!(customerRepository.findByCustomerId(customerId).getContactNumber().equals(customerDetails.getContactNumber())) &&
                customerRepository.existsByContactNumber(customerDetails.getContactNumber()))
        {
            return new ResponseEntity<>("Contact number is not available or taken! Please check again!", HttpStatus.BAD_REQUEST);
        }

        if (!bcrypt.matches(customerDetails.getPassword(), customerRepository.findByCustomerId(customerId).getPassword())){
            return new ResponseEntity<>("Incorrect password!", HttpStatus.UNAUTHORIZED);
        }

        updateProfile.setName(customerDetails.getName());
        //updateProfile.setEmail(customerDetails.getEmail());
        updateProfile.setContactNumber(customerDetails.getContactNumber());
        //updateProfile.setPassword(bcrypt.encode(customerDetails.getPassword()));

        customerRepository.save(updateProfile);

        return new ResponseEntity<Customer>(updateProfile, HttpStatus.OK);
    }

    // delete customer profile
    @DeleteMapping("/deleteCustomerProfile")
    public ResponseEntity<?> deleteCustomerProfile(@RequestParam(value="customerId") UUID customerId)
    {

        new ReservationService.deleteReservationByCustomerId(customerId);
        customerRepository.deleteById(customerId);

        return new ResponseEntity<>("Customer profile deleted successfully!", HttpStatus.OK);
    }

    @PutMapping("/updateLoyaltyPoints")
    public ResponseEntity<?> updateLoyaltyPoints(@Valid @RequestParam(value="contactNumber") String contactNumber,
                                                 @RequestParam(value="addedPoints") Integer addedPoints,
                                                 @RequestBody LoyaltyProgramRequestModel programDetails)
    {

        Customer updateProfile = customerRepository.findByContactNumber(contactNumber);

        updateProfile.setLoyaltyPoints(programDetails.getLoyaltyPoints()+addedPoints);

        customerRepository.save(updateProfile);

        return new ResponseEntity<Customer>(updateProfile, HttpStatus.OK);
    }

    @PutMapping("/redeemLoyaltyPoints")
    public ResponseEntity<?> redeemLoyaltyPoints(@Valid @RequestParam(value="contactNumber") String contactNumber,
                                                 @RequestParam(value="redeemPoints") Integer redeemPoints,
                                                 @RequestBody LoyaltyProgramRequestModel programDetails)
    {

        Customer updateProfile = customerRepository.findByContactNumber(contactNumber);

        updateProfile.setLoyaltyPoints(programDetails.getLoyaltyPoints()-redeemPoints);

        customerRepository.save(updateProfile);

        return new ResponseEntity<Customer>(updateProfile, HttpStatus.OK);
    }
}
