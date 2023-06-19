package com.fyp.restaurant.repository;

import com.fyp.restaurant.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, UUID> {

    Supplier findBySupplierId(UUID supplierId);


}
