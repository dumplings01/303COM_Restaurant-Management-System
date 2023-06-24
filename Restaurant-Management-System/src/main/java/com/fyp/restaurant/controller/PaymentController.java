package com.fyp.restaurant.controller;

import com.fyp.restaurant.model.Payment;
import com.fyp.restaurant.model.PaymentRequestModel;
import com.fyp.restaurant.model.Reservation;
import com.fyp.restaurant.repository.PaymentRepository;
import com.fyp.restaurant.repository.ReservationRepository;
import com.fyp.restaurant.service.SystemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path="/payment")
@CrossOrigin("http://localhost:3000")
public class PaymentController {

    private final PaymentRepository paymentRepository;

    private final ReservationRepository reservationRepository;

    public PaymentController(PaymentRepository paymentRepository, ReservationRepository reservationRepository){
        this.paymentRepository = paymentRepository;
        this.reservationRepository = reservationRepository;
    }

    // return all payment records
    @GetMapping("/getAllPayments")
    public List<Payment> getAllPayments()
    {
        return this.paymentRepository.findAll();
    }

    @PostMapping("/registerPaymentDetails")
    public ResponseEntity<?> registerPaymentDetails(@Valid @RequestParam(value="reservationId") UUID reservationId,
                                                        @RequestBody PaymentRequestModel payment)
    {
        Payment value = new SystemService().registerPaymentDetails(payment);

        paymentRepository.save(value);

        Reservation updateReservation = reservationRepository.findByReservationId(reservationId);
        updateReservation.setPaymentId(value.getPaymentId());
        updateReservation.setStatus("Deposit paid");

        reservationRepository.save(updateReservation);

        return new ResponseEntity<Payment>(value, HttpStatus.OK);
    }

    // delete record
    @DeleteMapping("/deletePayment")
    public ResponseEntity<?> deletePayment(@RequestParam(value="paymentId") UUID paymentId)
    {
        paymentRepository.deleteById(paymentId);
        return new ResponseEntity<>("Payment details deleted successfully!", HttpStatus.OK);
    }

}
