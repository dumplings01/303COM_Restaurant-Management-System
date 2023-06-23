package com.fyp.restaurant.controller;

import com.fyp.restaurant.model.ReservationSlots;
import com.fyp.restaurant.repository.ReservationSlotsRepository;
import com.fyp.restaurant.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path="/slots")
@CrossOrigin("http://localhost:3000")
public class ReservationSlotsController {

    private final ReservationSlotsRepository reservationSlotsRepository;

    public ReservationSlotsController(ReservationSlotsRepository reservationSlotsRepository) {
        this.reservationSlotsRepository = reservationSlotsRepository;
    }

    @Autowired
    private SystemService systemService;

    @PostMapping("/createSlots")
    public void createSlots(@Valid @RequestBody List<ReservationSlots> slots) {

       systemService.createSlots(slots);
    }
}
