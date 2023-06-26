package com.fyp.restaurant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fyp.restaurant.model.Payment;
import com.fyp.restaurant.model.PaymentRequestModel;
import com.fyp.restaurant.model.Reservation;
import com.fyp.restaurant.model.Supplier;
import com.fyp.restaurant.repository.PaymentRepository;
import com.fyp.restaurant.repository.ReservationRepository;
import com.fyp.restaurant.service.SystemService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SystemService systemService;

    @MockBean
    private PaymentRepository paymentRepository;

    @MockBean
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getAllPayments() throws Exception {
        Payment payment1 = new Payment();
        payment1.setPaymentId(UUID.randomUUID());
        payment1.setCardNumber("1234567890123456");
        payment1.setCardholderName("John Doe");
        payment1.setExpiryDate("12/23");
        payment1.setSecurityCode("123");
        payment1.setBillingAddress("123 Main St, City");

        Payment payment2 = new Payment();
        payment2.setPaymentId(UUID.randomUUID());
        payment2.setCardNumber("9876543210987654");
        payment2.setCardholderName("Jane Smith");
        payment2.setExpiryDate("06/25");
        payment2.setSecurityCode("456");
        payment2.setBillingAddress("456 Elm St, Town");

        List<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment1);
        paymentList.add(payment2);

        when(paymentRepository.findAll()).thenReturn((paymentList));

        mockMvc.perform(get("/payment/getAllPayments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].paymentId").value(payment1.getPaymentId().toString()))
                .andExpect(jsonPath("$[0].cardNumber").value(payment1.getCardNumber()))
                .andExpect(jsonPath("$[0].cardholderName").value(payment1.getCardholderName()))
                .andExpect(jsonPath("$[0].expiryDate").value(payment1.getExpiryDate()))
                .andExpect(jsonPath("$[0].securityCode").value(payment1.getSecurityCode()))
                .andExpect(jsonPath("$[0].billingAddress").value(payment1.getBillingAddress()))
                .andExpect(jsonPath("$[1].paymentId").value(payment2.getPaymentId().toString()))
                .andExpect(jsonPath("$[1].cardNumber").value(payment2.getCardNumber()))
                .andExpect(jsonPath("$[1].cardholderName").value(payment2.getCardholderName()))
                .andExpect(jsonPath("$[1].expiryDate").value(payment2.getExpiryDate()))
                .andExpect(jsonPath("$[1].securityCode").value(payment2.getSecurityCode()))
                .andExpect(jsonPath("$[1].billingAddress").value(payment2.getBillingAddress()));
    }

//    @Test
//    void registerPaymentDetails() throws Exception{
//
//        UUID reservationId = UUID.fromString("19d9ad70-d409-46bc-93ff-1e20150742fe");
//
//        Reservation reservation = new Reservation();
//        reservation.setReservationId(reservationId);
//        reservation.setCustomerId(UUID.randomUUID());
//        reservation.setCustomerName("John Doe");
//        reservation.setCustomerContact("1234567890");
//        reservation.setReservationDate("2023-06-26");
//        reservation.setNumberOfPeople(2);
//        reservation.setCustomerRemarks("No special remarks");
//        reservation.setCreatedAt(new Date());
//        reservation.setStatus("Pending");
//        reservation.setPaymentId(null);
//
//        reservationRepository.save(reservation);
//
//        // Create a sample payment request
//        Payment paymentRequest = new Payment();
//        paymentRequest.setCardNumber("1234567890123456");
//        paymentRequest.setCardholderName("John Doe");
//        paymentRequest.setExpiryDate("12/23");
//        paymentRequest.setSecurityCode("123");
//        paymentRequest.setBillingAddress("123 Main St, City");
//
//        when(reservationRepository.findByReservationId(reservationId)).thenReturn(reservation);
//
//        MvcResult result = mockMvc.perform(post("/payment/registerPaymentDetails")
//                        .param("reservationId", paymentRequest.getReservationId().toString())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(reservation)))
//                        .andExpect(status().isOk())
//                        .andReturn();
//
//        Reservation updatedReservation = objectMapper.readValue(
//                result.getResponse().getContentAsString(),
//                Reservation.class
//        );
//
//        Optional<Payment> savedPayment = paymentRepository.findById(updatedReservation.getPaymentId());
//        assertThat(savedPayment).isPresent();
//
//        assertThat(updatedReservation.getStatus()).isEqualTo("Deposit paid");
//    }

    @Test
    void deletePayment() throws Exception {

        Payment payment = new Payment();
        payment.setPaymentId(UUID.randomUUID());
        payment.setCardNumber("1234567890123456");
        payment.setCardholderName("John Doe");
        payment.setExpiryDate("12/23");
        payment.setSecurityCode("123");
        payment.setBillingAddress("123 Main St, City");
        paymentRepository.save(payment);

        // Perform the delete request
        mockMvc.perform(delete("/payment/deletePayment")
                        .param("paymentId", payment.getPaymentId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("Payment details deleted successfully!"));

        // Check if the payment is deleted
        Optional<Payment> deletedPayment = paymentRepository.findById(payment.getPaymentId());
        assertThat(deletedPayment).isEmpty();
    }
}