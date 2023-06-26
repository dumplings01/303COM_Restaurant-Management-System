package com.fyp.restaurant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fyp.restaurant.model.ReservationSlots;
import com.fyp.restaurant.model.ReservationSlotsRequestModel;
import com.fyp.restaurant.repository.ReservationSlotsRepository;
import com.fyp.restaurant.service.SystemService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservationSlotsController.class)
class ReservationSlotsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SystemService systemService;

    @MockBean
    private ReservationSlotsRepository reservationSlotsRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllSlots() throws Exception{
        ReservationSlots slot1 = new ReservationSlots(UUID.fromString("19d9ad70-d409-46bc-93ff-1e20150742fe"),
                new Date(), "10:00 AM", "Available", null);
        ReservationSlots slot2 = new ReservationSlots(UUID.fromString("35cc2a5e-2fc9-4e09-b5d0-88002d675fb0"),
                new Date(), "11:00 AM", "Booked", UUID.fromString("12345678-1234-1234-1234-1234567890ab"));

        List<ReservationSlots> slotsList = new ArrayList<>();
        slotsList.add(slot1);
        slotsList.add(slot2);

        when(reservationSlotsRepository.findAll()).thenReturn(slotsList);

        mockMvc.perform(get("/slots/getAllSlots")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].slotId").value("19d9ad70-d409-46bc-93ff-1e20150742fe"))
                .andExpect(jsonPath("$[0].date", is(notNullValue())))
                .andExpect(jsonPath("$[0].time").value("10:00 AM"))
                .andExpect(jsonPath("$[0].status").value("Available"))
                .andExpect(jsonPath("$[1].slotId").value("35cc2a5e-2fc9-4e09-b5d0-88002d675fb0"))
                .andExpect(jsonPath("$[1].date", is(notNullValue())))
                .andExpect(jsonPath("$[1].time").value("11:00 AM"))
                .andExpect(jsonPath("$[1].status").value("Booked"))
                .andExpect(jsonPath("$[1].reservationId").value("12345678-1234-1234-1234-1234567890ab"));
    }

    @Test
    void createSlots() throws Exception {

        List<ReservationSlots> slots = new ArrayList<>();

        mockMvc.perform(post("/slots/createSlots")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(slots)))
                .andExpect(status().isOk());
    }

    @Test
    void updateSlot() throws Exception{
        UUID slotId = UUID.randomUUID();
        ReservationSlotsRequestModel slotDetails = new ReservationSlotsRequestModel();

        ReservationSlots updateSlot = new ReservationSlots();

        when(reservationSlotsRepository.findBySlotId(slotId)).thenReturn(updateSlot);

        mockMvc.perform(put("/slots/updateSlot")
                        .param("slotId", slotId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(slotDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(slotDetails.getStatus()))
                .andExpect(jsonPath("$.reservationId").value(slotDetails.getReservationId()));
    }

    @Test
    void updateSlotByReservationId() throws Exception{
        UUID reservationId = UUID.randomUUID();
        ReservationSlotsRequestModel slotDetails = new ReservationSlotsRequestModel();

        ReservationSlots updateSlot = new ReservationSlots();

        when(reservationSlotsRepository.findByReservationId(reservationId)).thenReturn(updateSlot);

        // Perform the PUT request
        mockMvc.perform(put("/slots/updateSlotByReservationId")
                        .param("reservationId", reservationId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(slotDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(slotDetails.getStatus()))
                .andExpect(jsonPath("$.reservationId").value(slotDetails.getReservationId()));
    }

    @Test
    void deleteSlot() throws Exception{
        UUID slotId = UUID.randomUUID();

        // Perform the DELETE request
        mockMvc.perform(delete("/slots/deleteSlot")
                        .param("slotId", slotId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("Slot deleted successfully!"));
    }

    private String asJsonString(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}