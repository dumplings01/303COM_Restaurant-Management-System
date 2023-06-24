package com.fyp.restaurant.controller;

import com.fyp.restaurant.model.ReservationSlots;
import com.fyp.restaurant.model.ReservationSlotsRequestModel;
import com.fyp.restaurant.repository.ReservationSlotsRepository;
import com.fyp.restaurant.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

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

    // get all staffs
    @GetMapping("/getAllSlots")
    public List<ReservationSlots> getAllSlots ()
    {
        return this.reservationSlotsRepository.findAll();
    }

    @PostMapping("/createSlots")
    public void createSlots(@Valid @RequestBody List<ReservationSlots> slots) {
       systemService.createSlots(slots);
    }

    @PutMapping("/updateSlot")
    public ResponseEntity<?> updateSlot(@RequestParam(value="slotId") UUID slotId,
                                        @RequestBody ReservationSlotsRequestModel slotDetails)
    {
        ReservationSlots updateSlot = reservationSlotsRepository.findBySlotId(slotId);

        updateSlot.setStatus(slotDetails.getStatus());
        updateSlot.setReservationId(slotDetails.getReservationId());

        reservationSlotsRepository.save(updateSlot);

        return new ResponseEntity<ReservationSlots>(updateSlot, HttpStatus.OK);
    }

    @PutMapping("/updateSlotByReservationId")
    public ResponseEntity<?> updateSlotByReservationId(@RequestParam(value="reservationId") UUID reservationId,
                                                       @RequestBody ReservationSlotsRequestModel slotDetails)
    {
        ReservationSlots updateSlot = reservationSlotsRepository.findByReservationId(reservationId);

        updateSlot.setStatus(slotDetails.getStatus());
        updateSlot.setReservationId(slotDetails.getReservationId());

        reservationSlotsRepository.save(updateSlot);

        return new ResponseEntity<ReservationSlots>(updateSlot, HttpStatus.OK);
    }

    @DeleteMapping("/deleteSlot")
    public ResponseEntity<?> deleteSlot(@RequestParam(value="slotId") UUID slotId)
    {
        reservationSlotsRepository.deleteById(slotId);
        return new ResponseEntity<>("Slot deleted successfully!", HttpStatus.OK);
    }
}
