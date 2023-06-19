package com.fyp.restaurant.repository;

import com.fyp.restaurant.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository <Admin, UUID> {

    Admin findByAdminId(UUID adminId);

    Boolean existsByEmail (String email);

    Admin findByEmail (String email);
}
