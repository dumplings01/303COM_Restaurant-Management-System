package com.fyp.restaurant.repository;

import com.fyp.restaurant.model.Reservation;
import com.fyp.restaurant.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    Reservation findByReservationId(UUID reservationId);

    Boolean existsByCustomerId(UUID customerId);

    Boolean existsByReservationDate(Date reservationDate);

}
