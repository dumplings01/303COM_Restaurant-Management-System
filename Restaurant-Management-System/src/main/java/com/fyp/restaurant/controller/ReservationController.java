package com.fyp.restaurant.controller;

import com.fyp.restaurant.model.Reservation;
import com.fyp.restaurant.model.ReservationRequestModel;
import com.fyp.restaurant.repository.ReservationRepository;
import com.fyp.restaurant.service.SystemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path="/reservation")
@CrossOrigin("http://localhost:3000")
public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    // get all reservation
    @GetMapping("/getAllReservation")
    public Page<Reservation> getAllReservation(@RequestParam(defaultValue = "0") int currentPage)
    {
        Pageable pageable = PageRequest.of(currentPage, 3);
        return (Page<Reservation>) this.reservationRepository.findAll(pageable);
    }

    // get all reservation from single user
    @GetMapping("/getReservationsFromUser")
    public List<Reservation> getReservation (@RequestParam(name="customerId") UUID customerId)
    {

        return (List<Reservation>) this.reservationRepository.findByCustomerId(customerId);
    }

    // get single reservation
    @GetMapping("/getSingleReservation")
    public Reservation getSingleReservation (@RequestParam(name="reservationId") UUID reservationId)
    {
        return this.reservationRepository.findByReservationId(reservationId);
    }

    // create reservation
    @PostMapping("/createReservation")
    public ResponseEntity<?> createNewReservation(@Valid @RequestBody ReservationRequestModel reservation)
    {
        Reservation value = new SystemService().createReservation(reservation);

        reservationRepository.save(value);

        return new ResponseEntity<Reservation>(value, HttpStatus.OK);
    }

    // update reservation
    @PutMapping("/updateReservation")
    public ResponseEntity<?> updateReservation(@Valid @RequestParam(value="reservationId") UUID reservationId,
                                               @RequestBody ReservationRequestModel reservationDetails)
    {
        Reservation updateReservation = reservationRepository.findByReservationId(reservationId);

        updateReservation.setCustomerId(reservationDetails.getCustomerId());
        updateReservation.setCustomerName(reservationDetails.getCustomerName());
        updateReservation.setCustomerContact(reservationDetails.getCustomerContact());
        updateReservation.setReservationDate(reservationDetails.getReservationDate());
        updateReservation.setNumberOfPeople(reservationDetails.getNumberOfPeople());
        updateReservation.setCustomerRemarks(reservationDetails.getCustomerRemarks());

        reservationRepository.save(updateReservation);

        return new ResponseEntity<Reservation>(updateReservation, HttpStatus.OK);
    }


    // delete reservation
    @DeleteMapping("/deleteReservation")
    public ResponseEntity<?> deleteReservation(@RequestParam(value="reservationId") UUID reservationId)
    {
        reservationRepository.deleteById(reservationId);
        return new ResponseEntity<>("Reservation deleted successfully!", HttpStatus.OK);
    }
}
