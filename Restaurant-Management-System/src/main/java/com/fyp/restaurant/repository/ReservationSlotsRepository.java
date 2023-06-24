package com.fyp.restaurant.repository;

import com.fyp.restaurant.model.ReservationSlots;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.UUID;

@Repository
public interface ReservationSlotsRepository extends JpaRepository<ReservationSlots, UUID> {

    ReservationSlots findBySlotId(UUID slotId);

    ReservationSlots findByReservationId(UUID reservationId);

    boolean existsByDate(Date date);

    boolean existsByTime(String time);
}
