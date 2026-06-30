package org.cozy.HotelReservationSystem.controller;

import org.cozy.HotelReservationSystem.model.Reservation;
import org.cozy.HotelReservationSystem.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    // CREATE - Book a room
    @PostMapping
    public Reservation createReservation(@RequestBody Reservation reservation) {
        return reservationService.createReservation(reservation);
    }

    // READ - Get all reservations
    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    // READ - Get single reservation
    @GetMapping("/{id}")
    public Reservation getReservationById(@PathVariable Integer id) {
        return reservationService.getReservationById(id);
    }

    // UPDATE - Change reservation status
    @PutMapping("/{id}/status")
    public Reservation updateStatus(@PathVariable Integer id, @RequestParam String status) {
        return reservationService.updateReservationStatus(id, status);
    }

    // DELETE - Cancel reservation
    @DeleteMapping("/{id}")
    public String cancelReservation(@PathVariable Integer id) {
        reservationService.cancelReservation(id);
        return "Reservation cancelled successfully with id: " + id;
    }
}