package com.fyp.restaurant.controller;

import com.fyp.restaurant.model.Reservation;
import com.fyp.restaurant.model.ReservationRequestModel;
import com.fyp.restaurant.model.ReservationSlots;
import com.fyp.restaurant.repository.ReservationRepository;
import com.fyp.restaurant.repository.ReservationSlotsRepository;
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

    private final ReservationSlotsRepository reservationSlotsRepository;

    public ReservationController(ReservationRepository reservationRepository, ReservationSlotsRepository reservationSlotsRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationSlotsRepository = reservationSlotsRepository;
    }

    // get all reservation
    @GetMapping("/getAllReservation")
    public List<Reservation> getAllReservation()
    {
        return this.reservationRepository.findAll();
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

        if (reservationRepository.countByCustomerId(value.getCustomerId()) >= 2)
        {
            return new ResponseEntity<>("Customer can only create maximum of two ongoing and future reservations!", HttpStatus.BAD_REQUEST);
        }

        reservationRepository.save(value);

        return new ResponseEntity<Reservation>(value, HttpStatus.OK);
    }

    // update reservation
    @PutMapping("/updateReservation")
    public ResponseEntity<?> updateReservation(@Valid @RequestParam(value="reservationId") UUID reservationId,
                                               @RequestBody ReservationRequestModel reservationDetails)
    {
        Reservation updateReservation = reservationRepository.findByReservationId(reservationId);
        ReservationSlots updateSlot = reservationSlotsRepository.findByReservationId(reservationId);

        if (!(reservationDetails.getReservationDate().equals(updateReservation.getReservationDate()))){
            updateSlot.setStatus("Available");
            updateSlot.setReservationId(null);
        }

        updateReservation.setCustomerId(reservationDetails.getCustomerId());
        updateReservation.setCustomerName(reservationDetails.getCustomerName());
        updateReservation.setCustomerContact(reservationDetails.getCustomerContact());
        updateReservation.setReservationDate(reservationDetails.getReservationDate());
        updateReservation.setNumberOfPeople(reservationDetails.getNumberOfPeople());
        updateReservation.setCustomerRemarks(reservationDetails.getCustomerRemarks());
        updateReservation.setStatus(reservationDetails.getStatus());

        reservationRepository.save(updateReservation);

        return new ResponseEntity<Reservation>(updateReservation, HttpStatus.OK);
    }

    // update status
    @PutMapping("/updateReservationPayment")
    public ResponseEntity<?> updateReservationPayment(@Valid @RequestParam(value="reservationId") UUID reservationId,
                                                     @RequestBody ReservationRequestModel reservationDetails)
    {
        Reservation updateReservation = reservationRepository.findByReservationId(reservationId);

        updateReservation.setStatus(reservationDetails.getStatus());

        reservationRepository.save(updateReservation);

        return new ResponseEntity<Reservation>(updateReservation, HttpStatus.OK);
    }


    // delete reservation
    @DeleteMapping("/cancelReservation")
    public ResponseEntity<?> deleteReservation(@RequestParam(value="reservationId") UUID reservationId)
    {
        ReservationSlots updateSlot = reservationSlotsRepository.findByReservationId(reservationId);

        updateSlot.setStatus("Available");
        updateSlot.setReservationId(null);

        reservationRepository.deleteById(reservationId);
        return new ResponseEntity<>("Reservation deleted successfully!", HttpStatus.OK);
    }
}
