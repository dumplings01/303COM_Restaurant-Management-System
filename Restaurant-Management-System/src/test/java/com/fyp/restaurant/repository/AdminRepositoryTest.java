package com.fyp.restaurant.repository;

import com.fyp.restaurant.model.Admin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
public class AdminRepositoryTest {

    @Autowired
    private AdminRepository adminRepository;
    Admin admin1;
    Admin admin2;
    Admin admin3;
    Admin admin4;

    @BeforeEach
    void setUp() {

        List<String> roles = new ArrayList<>();
        roles.add("role_1");
        roles.add("role_2");

        admin1 = new Admin(UUID.fromString("19d9ad70-d409-46bc-93ff-1e20150742fe"),"Jack","jack@gmail.com","jack1234",roles);
        admin2 = new Admin(UUID.fromString("45e12368-0251-4d6f-984f-ab7d1acb21e6"),"Yan","yan@gmail.com","j4343grr",roles);
        admin3 = new Admin(UUID.fromString("72efa8db-4f70-4c31-a8e7-19a807a7a894"),"Koay","koay@gmail.com","j4344ggr",roles);
        admin4 = new Admin(UUID.fromString("35cc2a5e-2fc9-4e09-b5d0-88002d675fb0"),"Ma","ma@gmail.com","5ttt7hhff",roles);

        adminRepository.save(admin1);
        adminRepository.save(admin2);
        adminRepository.save(admin3);
        adminRepository.save(admin4);
    }

    @AfterEach
    void tearDown() {
        admin1 = null;
        admin2 = null;
        admin3 = null;
        admin4 = null;
        adminRepository.deleteAll();

    }

    @Test
    void testExistByEmail_True()
    {
        assertTrue(adminRepository.existsByEmail("jack@gmail.com"));
        assertTrue(adminRepository.existsByEmail("yan@gmail.com"));
        assertTrue(adminRepository.existsByEmail("koay@gmail.com"));
        assertTrue(adminRepository.existsByEmail("ma@gmail.com"));
    }

    @Test
    void testExistByEmail_False()
    {
        assertFalse(adminRepository.existsByEmail("notJack"));
        assertFalse(adminRepository.existsByEmail("notYan"));
        assertFalse(adminRepository.existsByEmail("notKoay"));
        assertFalse(adminRepository.existsByEmail("notMa"));
    }

    @Test
    void testFindByEmail_Found()
    {
        Admin admin = adminRepository.findByEmail("jack@gmail.com");
        assertThat(admin.getAdminId()).isEqualTo(admin.getAdminId());
        assertThat(admin.getName()).isEqualTo(admin.getName());
    }

    @Test
    void testFindByEmail_NotFound()
    {
        Optional<Admin> admin = Optional.ofNullable(adminRepository.findByEmail("abc@gmail.com"));
        assertFalse(admin.isPresent());
    }
}
