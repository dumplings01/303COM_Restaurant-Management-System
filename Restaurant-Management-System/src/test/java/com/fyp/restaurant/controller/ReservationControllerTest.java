package com.fyp.restaurant.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fyp.restaurant.model.Reservation;
import com.fyp.restaurant.model.ReservationRequestModel;
import com.fyp.restaurant.model.ReservationSlots;
import com.fyp.restaurant.repository.ReservationRepository;
import com.fyp.restaurant.repository.ReservationSlotsRepository;
import com.fyp.restaurant.service.SystemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SystemService systemService;

    @MockBean
    private ReservationRepository reservationRepository;

    @MockBean
    private ReservationSlotsRepository reservationSlotsRepository;


    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getAllReservation() throws Exception {
        Reservation reservation1 = new Reservation();
        reservation1.setReservationId(UUID.fromString("19d9ad70-d409-46bc-93ff-1e20150742fe"));
        reservation1.setCustomerId(UUID.fromString("c56a4180-65aa-42ec-a945-5fd21dec0501"));
        reservation1.setCustomerName("John Doe");
        reservation1.setCustomerContact("1234567890");
        reservation1.setReservationDate("2023-06-26");
        reservation1.setNumberOfPeople(4);
        reservation1.setCustomerRemarks("Sample remarks");
        reservation1.setCreatedAt(new Date());
        reservation1.setStatus("Active");
        reservation1.setPaymentId(UUID.fromString("2e14d5d8-4b38-4e0d-9e48-df849d0f66d2"));

        Reservation reservation2 = new Reservation();
        reservation2.setReservationId(UUID.fromString("581c9906-d5c0-4b2c-b374-06f8b4763f5e"));
        reservation2.setCustomerId(UUID.fromString("8f61688f-2d57-4788-a0d6-29f0f9a13c0b"));
        reservation2.setCustomerName("Jane Smith");
        reservation2.setCustomerContact("9876543210");
        reservation2.setReservationDate("2023-06-27");
        reservation2.setNumberOfPeople(2);
        reservation2.setCustomerRemarks("Another sample remarks");
        reservation2.setCreatedAt(new Date());
        reservation2.setStatus("Active");
        reservation2.setPaymentId(UUID.fromString("7a4df10d-98c5-4d68-9467-297b6148cc46"));

        List<Reservation> reservationList = new ArrayList<>();
        reservationList.add(reservation1);
        reservationList.add(reservation2);

        when(reservationRepository.findAll()).thenReturn(reservationList);

        mockMvc.perform(get("/reservation/getAllReservation")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].reservationId").value("19d9ad70-d409-46bc-93ff-1e20150742fe"))
                .andExpect(jsonPath("$[0].customerId").value("c56a4180-65aa-42ec-a945-5fd21dec0501"))
                .andExpect(jsonPath("$[0].customerName").value("John Doe"))
                .andExpect(jsonPath("$[0].customerContact").value("1234567890"))
                .andExpect(jsonPath("$[0].reservationDate").value("2023-06-26"))
                .andExpect(jsonPath("$[0].numberOfPeople").value(4))
                .andExpect(jsonPath("$[0].customerRemarks").value("Sample remarks"))
                .andExpect(jsonPath("$[0].createdAt").isNotEmpty())
                .andExpect(jsonPath("$[0].status").value("Active"))
                .andExpect(jsonPath("$[0].paymentId").value("2e14d5d8-4b38-4e0d-9e48-df849d0f66d2"))
                .andExpect(jsonPath("$[1].reservationId").value("581c9906-d5c0-4b2c-b374-06f8b4763f5e"))
                .andExpect(jsonPath("$[1].customerId").value("8f61688f-2d57-4788-a0d6-29f0f9a13c0b"))
                .andExpect(jsonPath("$[1].customerName").value("Jane Smith"))
                .andExpect(jsonPath("$[1].customerContact").value("9876543210"))
                .andExpect(jsonPath("$[1].reservationDate").value("2023-06-27"))
                .andExpect(jsonPath("$[1].numberOfPeople").value(2))
                .andExpect(jsonPath("$[1].customerRemarks").value("Another sample remarks"))
                .andExpect(jsonPath("$[1].createdAt").isNotEmpty())
                .andExpect(jsonPath("$[1].status").value("Active"))
                .andExpect(jsonPath("$[1].paymentId").value("7a4df10d-98c5-4d68-9467-297b6148cc46"));
    }

    @Test
    void getReservationfromUser() throws Exception {

        UUID customerId = UUID.fromString("c56a4180-65aa-42ec-a945-5fd21dec0501");

        Reservation reservation1 = new Reservation();
        reservation1.setReservationId(UUID.fromString("19d9ad70-d409-46bc-93ff-1e20150742fe"));
        reservation1.setCustomerId(UUID.fromString("c56a4180-65aa-42ec-a945-5fd21dec0501"));
        reservation1.setCustomerName("John Doe");
        reservation1.setCustomerContact("1234567890");
        reservation1.setReservationDate("2023-06-26");
        reservation1.setNumberOfPeople(4);
        reservation1.setCustomerRemarks("Sample remarks");
        reservation1.setCreatedAt(new Date());
        reservation1.setStatus("Active");
        reservation1.setPaymentId(UUID.fromString("2e14d5d8-4b38-4e0d-9e48-df849d0f66d2"));

        Reservation reservation2 = new Reservation();
        reservation2.setReservationId(UUID.fromString("581c9906-d5c0-4b2c-b374-06f8b4763f5e"));
        reservation2.setCustomerId(UUID.fromString("8f61688f-2d57-4788-a0d6-29f0f9a13c0b"));
        reservation2.setCustomerName("Jane Smith");
        reservation2.setCustomerContact("9876543210");
        reservation2.setReservationDate("2023-06-27");
        reservation2.setNumberOfPeople(2);
        reservation2.setCustomerRemarks("Another sample remarks");
        reservation2.setCreatedAt(new Date());
        reservation2.setStatus("Active");
        reservation2.setPaymentId(UUID.fromString("7a4df10d-98c5-4d68-9467-297b6148cc46"));

        List<Reservation> reservationList = new ArrayList<>();
        reservationList.add(reservation1);
        reservationList.add(reservation2);

        when(reservationRepository.findByCustomerId(any())).thenReturn(reservationList);

        mockMvc.perform(get("/reservation/getReservationsFromUser")
                        .param("customerId", customerId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].reservationId").value("19d9ad70-d409-46bc-93ff-1e20150742fe"))
                .andExpect(jsonPath("$[0].customerId").value("c56a4180-65aa-42ec-a945-5fd21dec0501"))
                .andExpect(jsonPath("$[0].customerName").value("John Doe"))
                .andExpect(jsonPath("$[0].customerContact").value("1234567890"))
                .andExpect(jsonPath("$[0].reservationDate").value("2023-06-26"))
                .andExpect(jsonPath("$[0].numberOfPeople").value(4))
                .andExpect(jsonPath("$[0].customerRemarks").value("Sample remarks"))
                .andExpect(jsonPath("$[0].createdAt").isNotEmpty())
                .andExpect(jsonPath("$[0].status").value("Active"))
                .andExpect(jsonPath("$[0].paymentId").value("2e14d5d8-4b38-4e0d-9e48-df849d0f66d2"));
    }

    @Test
    void getSingleReservation() throws Exception {
        UUID reservationId = UUID.fromString("19d9ad70-d409-46bc-93ff-1e20150742fe");

        Reservation reservation = new Reservation();
        reservation.setReservationId(reservationId);
        reservation.setCustomerId(UUID.fromString("c56a4180-65aa-42ec-a945-5fd21dec0501"));
        reservation.setCustomerName("John Doe");
        reservation.setCustomerContact("1234567890");
        reservation.setReservationDate("2023-06-26");
        reservation.setNumberOfPeople(4);
        reservation.setCustomerRemarks("Sample remarks");
        reservation.setCreatedAt(new Date());
        reservation.setStatus("Active");
        reservation.setPaymentId(UUID.fromString("2e14d5d8-4b38-4e0d-9e48-df849d0f66d2"));

        when(reservationRepository.findByReservationId(any())).thenReturn(reservation);

        mockMvc.perform(get("/reservation/getSingleReservation")
                        .param("reservationId", reservationId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.reservationId").value("19d9ad70-d409-46bc-93ff-1e20150742fe"))
                .andExpect(jsonPath("$.customerId").value("c56a4180-65aa-42ec-a945-5fd21dec0501"))
                .andExpect(jsonPath("$.customerName").value("John Doe"))
                .andExpect(jsonPath("$.customerContact").value("1234567890"))
                .andExpect(jsonPath("$.reservationDate").value("2023-06-26"))
                .andExpect(jsonPath("$.numberOfPeople").value(4))
                .andExpect(jsonPath("$.customerRemarks").value("Sample remarks"))
                .andExpect(jsonPath("$.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.status").value("Active"))
                .andExpect(jsonPath("$.paymentId").value("2e14d5d8-4b38-4e0d-9e48-df849d0f66d2"));
    }

    @Test
    void createNewReservation() throws Exception {
        ReservationRequestModel reservationRequestModel = new ReservationRequestModel();
        reservationRequestModel.setCustomerId(UUID.fromString("c56a4180-65aa-42ec-a945-5fd21dec0501"));
        reservationRequestModel.setCustomerName("John Doe");
        reservationRequestModel.setCustomerContact("1234567890");
        reservationRequestModel.setReservationDate("2023-06-26");
        reservationRequestModel.setNumberOfPeople(4);
        reservationRequestModel.setCustomerRemarks("Sample remarks");
        reservationRequestModel.setStatus("Active");
        reservationRequestModel.setPaymentId(UUID.fromString("2e14d5d8-4b38-4e0d-9e48-df849d0f66d2"));

        Reservation reservation = new Reservation();
        reservation.setReservationId(UUID.fromString("19d9ad70-d409-46bc-93ff-1e20150742fe"));
        reservation.setCustomerId(UUID.fromString("c56a4180-65aa-42ec-a945-5fd21dec0501"));
        reservation.setCustomerName("John Doe");
        reservation.setCustomerContact("1234567890");
        reservation.setReservationDate("2023-06-26");
        reservation.setNumberOfPeople(4);
        reservation.setCustomerRemarks("Sample remarks");
        reservation.setCreatedAt(new Date());
        reservation.setStatus("Active");
        reservation.setPaymentId(UUID.fromString("2e14d5d8-4b38-4e0d-9e48-df849d0f66d2"));

        when(systemService.createReservation(any())).thenReturn(reservation);

        when(reservationRepository.countByCustomerId(any())).thenReturn(Math.toIntExact(1L));

        mockMvc.perform(post("/reservation/createReservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationRequestModel)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateReservation() throws Exception{

        UUID reservationId = UUID.fromString("19d9ad70-d409-46bc-93ff-1e20150742fe");

        ReservationRequestModel reservation = new ReservationRequestModel();
        reservation.setCustomerId(UUID.fromString("c56a4180-65aa-42ec-a945-5fd21dec0501"));
        reservation.setCustomerName("John Doe");
        reservation.setCustomerContact("1234567890");
        reservation.setReservationDate("2023-06-26");
        reservation.setNumberOfPeople(4);
        reservation.setCustomerRemarks("Sample remarks");
        reservation.setStatus("Active");
        reservation.setPaymentId(UUID.fromString("2e14d5d8-4b38-4e0d-9e48-df849d0f66d2"));

        Reservation updatedReservation = new Reservation();
        updatedReservation.setReservationId(reservationId);
        updatedReservation.setCustomerId(UUID.randomUUID());
        updatedReservation.setCustomerName("Updated Customer");
        updatedReservation.setCustomerContact("1234567890");
        updatedReservation.setReservationDate("2023-06-27");
        updatedReservation.setNumberOfPeople(4);
        updatedReservation.setCustomerRemarks("Updated remarks");
        updatedReservation.setStatus("Updated status");

        when(reservationRepository.findByReservationId(reservationId)).thenReturn(updatedReservation);

        mockMvc.perform(put("/reservation/updateReservation")
                        .param("reservationId", reservationId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedReservation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(updatedReservation.getCustomerId().toString()))
                .andExpect(jsonPath("$.customerName").value(updatedReservation.getCustomerName()))
                .andExpect(jsonPath("$.customerContact").value(updatedReservation.getCustomerContact()))
                .andExpect(jsonPath("$.reservationDate").value(updatedReservation.getReservationDate()))
                .andExpect(jsonPath("$.numberOfPeople").value(updatedReservation.getNumberOfPeople()))
                .andExpect(jsonPath("$.customerRemarks").value(updatedReservation.getCustomerRemarks()))
                .andExpect(jsonPath("$.status").value(updatedReservation.getStatus()));
    }

    @Test
    void updateReservationPayment() throws Exception {

        UUID reservationId = UUID.fromString("19d9ad70-d409-46bc-93ff-1e20150742fe");

        Reservation reservation = new Reservation();
        reservation.setReservationId(reservationId);
        reservation.setCustomerId(UUID.fromString("c56a4180-65aa-42ec-a945-5fd21dec0501"));
        reservation.setCustomerName("John Doe");
        reservation.setCustomerContact("1234567890");
        reservation.setReservationDate("2023-06-26");
        reservation.setNumberOfPeople(4);
        reservation.setCustomerRemarks("Sample remarks");
        reservation.setStatus("Active");
        reservation.setPaymentId(UUID.fromString("2e14d5d8-4b38-4e0d-9e48-df849d0f66d2"));

        ReservationRequestModel updatedReservation = new ReservationRequestModel();
        updatedReservation.setStatus("Paid");

        when(reservationRepository.findByReservationId(reservationId)).thenReturn(reservation);

        mockMvc.perform(put("/reservation/updateReservationPayment")
                        .param("reservationId", reservationId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedReservation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservationId").value(reservationId.toString()))
                .andExpect(jsonPath("$.status").value(updatedReservation.getStatus()));
    }
}