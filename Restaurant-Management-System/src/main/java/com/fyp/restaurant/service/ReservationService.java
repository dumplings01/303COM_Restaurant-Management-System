package com.fyp.restaurant.service;

import com.fyp.restaurant.model.Reservation;
import com.fyp.restaurant.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReservationService {

    private static ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        ReservationService.reservationRepository = reservationRepository;
    }

    public static class deleteReservationByCustomerId {
        public deleteReservationByCustomerId(UUID customerId) {
            List<Reservation> reservations = reservationRepository.findByCustomerId(customerId);
            for (Reservation reservation : reservations) {
                reservationRepository.delete(reservation);
            }
        }
    }
}
