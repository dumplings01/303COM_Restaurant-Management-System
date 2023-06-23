package com.fyp.restaurant.controller;

import com.fyp.restaurant.model.Admin;
import com.fyp.restaurant.model.AdminRequestModel;
import com.fyp.restaurant.repository.AdminRepository;
import com.fyp.restaurant.repository.CustomerRepository;
import com.fyp.restaurant.service.SystemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path="/admin")
@CrossOrigin("http://localhost:3000")
public class AdminController {

    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;


    public AdminController(AdminRepository adminRepository, CustomerRepository customerRepository) {
        this.adminRepository = adminRepository;
        this.customerRepository = customerRepository;
    }

    // get admin profile
    @GetMapping("/getAdminProfile")
    public Admin getAdminProfile (@RequestParam(name="adminId") UUID adminId)
    {
        return this.adminRepository.findByAdminId(adminId);
    }

    // get admin profile
    @GetMapping("/getAdminRoleByEmail")
    public Admin getAdminRole (@RequestParam(name="email") String email)
    {
        return this.adminRepository.findByEmail(email);
    }

    // get all staffs
    @GetMapping("/getAllStaffs")
    public List<Admin> getAllStaffs ()
    {
        return this.adminRepository.findAll();
    }

    // create admin profile
    @PostMapping("/createNewAdmin")
    public ResponseEntity<?> createNewAdmin(@Valid @RequestBody AdminRequestModel admin)
    {
        Admin value = new SystemService().createStaffAccount(admin);

        if (customerRepository.existsByEmail(value.getEmail()) || adminRepository.existsByEmail(value.getEmail())){
            return new ResponseEntity<>("Email already taken!", HttpStatus.BAD_REQUEST);
        }

        adminRepository.save(value);

        return new ResponseEntity<Admin>(value, HttpStatus.OK);
    }

    @PutMapping("/updateAdminProfile")
    public ResponseEntity<?> updateAdminProfile(@Valid @RequestParam(value="adminId") UUID adminId,
                                                @RequestBody AdminRequestModel adminDetails)
    {
        Admin updateProfile = adminRepository.findByAdminId(adminId);

        updateProfile.setName(adminDetails.getName());
        updateProfile.setRoles(adminDetails.getRoles());

        adminRepository.save(updateProfile);

        return new ResponseEntity<Admin>(updateProfile, HttpStatus.OK);
    }

    // delete admin profile
    @DeleteMapping("/deleteAdminProfile")
    public ResponseEntity<?> deleteAdminProfile(@RequestParam(value="adminId") UUID adminId)
    {
        adminRepository.deleteById(adminId);
        return new ResponseEntity<>("Admin profile deleted successfully!", HttpStatus.OK);
    }
}
