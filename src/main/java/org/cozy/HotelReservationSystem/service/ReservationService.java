package org.cozy.HotelReservationSystem.service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.cozy.HotelReservationSystem.model.Reservation;
import org.cozy.HotelReservationSystem.model.Room;
import org.cozy.HotelReservationSystem.repository.ReservationRepository;
import org.cozy.HotelReservationSystem.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RoomRepository roomRepository;

    // CREATE - Book a room
    public Reservation createReservation(Reservation reservation) {
        // Get the room
        Room room = roomRepository.findById(reservation.getRoom().getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // Set default status
        reservation.setStatus("Pending");

        // Calculate total price based on number of nights
        long nights = ChronoUnit.DAYS.between(reservation.getCheckInDate(), reservation.getCheckOutDate());
        BigDecimal totalPrice = room.getPricePerNight().multiply(BigDecimal.valueOf(nights));
        reservation.setTotalPrice(totalPrice);

        // Mark room as booked
        room.setStatus("Booked");
        roomRepository.save(room);

        return reservationRepository.save(reservation);
    }

    // READ - All reservations
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    // READ - Single reservation by ID
    public Reservation getReservationById(Integer id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found with id: " + id));
    }

    // UPDATE - Change reservation status
    public Reservation updateReservationStatus(Integer id, String status) {
        Reservation reservation = getReservationById(id);
        reservation.setStatus(status);
        return reservationRepository.save(reservation);
    }

    // UPDATE - Check out a reservation
    public Reservation checkoutReservation(Integer id) {
        Reservation reservation = getReservationById(id);

        // Make room available again after checkout
        Room room = reservation.getRoom();
        room.setStatus("Available");
        roomRepository.save(room);

        reservation.setStatus("Checked Out");
        return reservationRepository.save(reservation);
    }

    // DELETE - Cancel reservation
    public void cancelReservation(Integer id) {
        Reservation reservation = getReservationById(id);

        // Make room available again
        Room room = reservation.getRoom();
        room.setStatus("Available");
        roomRepository.save(room);

        reservation.setStatus("Cancelled");
        reservationRepository.save(reservation);
    }
}