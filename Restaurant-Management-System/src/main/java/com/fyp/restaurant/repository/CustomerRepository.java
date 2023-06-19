package com.fyp.restaurant.repository;

import com.fyp.restaurant.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository <Customer, UUID> {
    Customer findByCustomerId(UUID customerId);

    Boolean existsByEmail(String email);

    Boolean existsByContactNumber(String contactNumber);

    Customer findByEmail(String email);

}
