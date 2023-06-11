package com.fyp.restaurant.controller;

import com.fyp.restaurant.model.Admin;
import com.fyp.restaurant.model.AdminRequestModel;
import com.fyp.restaurant.repository.AdminRepository;
import com.fyp.restaurant.service.SystemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path="/admin")
@CrossOrigin("http://localhost:3000")
public class AdminController {

    private final AdminRepository adminRepository;

    public AdminController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    // get admin profile
    @GetMapping("/getAdminProfile")
    public Admin getAdminProfile (@RequestParam(name="adminId") UUID adminId)
    {
        return this.adminRepository.findByAdminId(adminId);
    }

    // create admin profile
    @PostMapping("/createNewAdmin")
    public ResponseEntity<?> createNewAdmin(@Valid @RequestBody AdminRequestModel admin)
    {
        Admin value = new SystemService().createStaffAccount(admin);

        adminRepository.save(value);

        return new ResponseEntity<Admin>(value, HttpStatus.OK);
    }

    // delete admin profile
    @DeleteMapping("/deleteAdminProfile")
    public ResponseEntity<?> deleteAdminProfile(@RequestParam(value="adminId") UUID adminId)
    {
        adminRepository.deleteById(adminId);
        return new ResponseEntity<>("Admin profile deleted successfully!", HttpStatus.OK);
    }
}
