package com.fyp.restaurant.repository;

import com.fyp.restaurant.model.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    Customer customer1;
    Customer customer2;
    Customer customer3;
    Customer customer4;

    @BeforeEach
    void setUp() {
        customer1 = new Customer(UUID.fromString("19d9ad70-d409-46bc-93ff-1e20150742fe"),"Jack","jack@gmail.com","jack1234","012-2323232", 23);
        customer2 = new Customer(UUID.fromString("45e12368-0251-4d6f-984f-ab7d1acb21e6"),"Yan","yan@gmail.com","j4343grr","012-2323356", 38);
        customer3 = new Customer(UUID.fromString("72efa8db-4f70-4c31-a8e7-19a807a7a894"),"Koay","koay@gmail.com","j4344ggr","012-2353732", 73);
        customer4 = new Customer(UUID.fromString("35cc2a5e-2fc9-4e09-b5d0-88002d675fb0"),"Ma","ma@gmail.com","5ttt7hhff","012-2324352", 36);

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);
        customerRepository.save(customer4);
    }

    @AfterEach
    void tearDown() {
        customer1 = null;
        customer2 = null;
        customer3 = null;
        customer4 = null;
        customerRepository.deleteAll();
    }

    @Test
    void testExistByEmail_True()
    {
        assertTrue(customerRepository.existsByEmail("jack@gmail.com"));
        assertTrue(customerRepository.existsByEmail("yan@gmail.com"));
        assertTrue(customerRepository.existsByEmail("koay@gmail.com"));
        assertTrue(customerRepository.existsByEmail("ma@gmail.com"));
    }

    @Test
    void testExistByEmail_False()
    {
        assertFalse(customerRepository.existsByEmail("notJack"));
        assertFalse(customerRepository.existsByEmail("notYan"));
        assertFalse(customerRepository.existsByEmail("notKoay"));
        assertFalse(customerRepository.existsByEmail("notMa"));
    }

    @Test
    void testExistByContactNumber_True()
    {
        assertTrue(customerRepository.existsByContactNumber("012-2323232"));
        assertTrue(customerRepository.existsByContactNumber("012-2323356"));
        assertTrue(customerRepository.existsByContactNumber("012-2353732"));
        assertTrue(customerRepository.existsByContactNumber("012-2324352"));
    }

    @Test
    void testExistByContactNumber_False()
    {
        assertFalse(customerRepository.existsByContactNumber("013-2323274"));
        assertFalse(customerRepository.existsByContactNumber("017-2345232"));
        assertFalse(customerRepository.existsByContactNumber("013-2323452"));
        assertFalse(customerRepository.existsByContactNumber("017-2324532"));
    }

    @Test
    void testFindByEmail_Found()
    {
        Customer customer = customerRepository.findByEmail("jack@gmail.com");
        assertThat(customer.getCustomerId()).isEqualTo(customer.getCustomerId());
        assertThat(customer.getName()).isEqualTo(customer.getName());
    }

    @Test
    void testFindByEmail_NotFound()
    {
        Optional<Customer> customer = Optional.ofNullable(customerRepository.findByEmail("abc@gmail.com"));
        assertFalse(customer.isPresent());
    }
}
