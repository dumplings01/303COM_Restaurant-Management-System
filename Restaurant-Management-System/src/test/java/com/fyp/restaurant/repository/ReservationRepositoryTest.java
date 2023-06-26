package com.fyp.restaurant.repository;

import com.fyp.restaurant.model.Reservation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;
    Reservation reservation;

    @BeforeEach
    void setUp() {
        reservation = new Reservation(UUID.fromString("19d9ad70-d409-46bc-93ff-1e20150742fe"),UUID.fromString("19d9ad70-d409-46bc-93ff-1e20150742fe"),
                "Jack","012-3332432","2023-09-23 10:00AM", 9, "Testing remarks", new Date(),
                "Deposit pending", UUID.fromString("19d9ad70-d409-46bc-93ff-1e20150742fe"));

        reservationRepository.save(reservation);
    }

    @AfterEach
    void tearDown() {
        reservation = null;
        reservationRepository.deleteAll();
    }

    @Test
    void testCountByCustomerId(){
        assertThat(reservationRepository.countByCustomerId(UUID.fromString("19d9ad70-d409-46bc-93ff-1e20150742fe"))).isNotNull();
    }
}