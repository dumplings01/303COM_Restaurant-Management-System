package com.fyp.restaurant.repository;

import com.fyp.restaurant.model.Stock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
public class StockRepositoryTest {

    @Autowired
    private StockRepository stockRepository;
    Stock stock;

    @BeforeEach
    void setUp() {
        stock = new Stock(UUID.fromString("19d9ad70-d409-46bc-93ff-1e20150742fe"), "Rice",
                "Grains", 23, null, null, 20, new Date());

        stockRepository.save(stock);
    }

    @AfterEach
    void tearDown() {
        stock = null;
        stockRepository.deleteAll();
    }

    @Test
    void testExistsByName_True() {
        assertTrue(stockRepository.existsByName("Rice"));
    }

    @Test
    void testExistsByName_False() {
        assertFalse(stockRepository.existsByName("XYZ"));
    }
}
