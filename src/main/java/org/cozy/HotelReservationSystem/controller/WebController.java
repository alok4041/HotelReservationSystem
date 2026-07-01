package org.cozy.HotelReservationSystem.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.cozy.HotelReservationSystem.model.Customer;
import org.cozy.HotelReservationSystem.model.Reservation;
import org.cozy.HotelReservationSystem.model.Room;
import org.cozy.HotelReservationSystem.repository.CustomerRepository;
import org.cozy.HotelReservationSystem.service.CustomerService;
import org.cozy.HotelReservationSystem.service.ReservationService;
import org.cozy.HotelReservationSystem.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {

    @Autowired private CustomerService customerService;
    @Autowired private RoomService roomService;
    @Autowired private ReservationService reservationService;
    @Autowired private CustomerRepository customerRepository;

    // --- DASHBOARD & PUBLIC FLOW ---
    @GetMapping("/")
    public String viewHomePage() {
        return "dashboard";
    }

    @GetMapping("/book")
    public String viewBookingPage(Model model) {
        model.addAttribute("availableRooms", roomService.getAllRooms().stream()
                .filter(room -> "Available".equals(room.getStatus()))
                .collect(Collectors.toList()));
        return "book";
    }

    @PostMapping("/book/submit")
    public String submitBooking(
            @RequestParam String name, @RequestParam String email, @RequestParam String phone,
            @RequestParam Integer roomId, @RequestParam LocalDate checkInDate,
            @RequestParam LocalDate checkOutDate, @RequestParam Integer numberOfGuests) {
        
        Customer customer = customerService.createCustomer(new Customer(null, name, email, phone, null, null));
        Reservation reservation = new Reservation();
        reservation.setCustomer(customer);
        reservation.setRoom(roomService.getRoomById(roomId));
        reservation.setCheckInDate(checkInDate);
        reservation.setCheckOutDate(checkOutDate);
        reservation.setNumberOfGuests(numberOfGuests);
        reservationService.createReservation(reservation);
        return "redirect:/?success=true";
    }

    @GetMapping("/search")
    public String searchReservations(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("keyword", keyword);
        if (keyword != null && !keyword.isEmpty()) {
            // Use the new method name here
            List<Customer> matchingCustomers = customerRepository.searchCustomers(keyword);
            
            List<Reservation> filtered = reservationService.getAllReservations().stream()
                    .filter(res -> matchingCustomers.contains(res.getCustomer()))
                    .collect(Collectors.toList());
            model.addAttribute("results", filtered);
        } else {
            model.addAttribute("results", new ArrayList<>());
        }
        return "search";
    }

    // --- ADMIN MANAGEMENT ---
    @GetMapping("/web/customers")
    public String viewCustomersPage(Model model) {
        model.addAttribute("listCustomers", customerService.getAllCustomers());
        model.addAttribute("newCustomer", new Customer());
        return "customers";
    }

    @PostMapping("/web/customers/save")
    public String saveCustomer(@ModelAttribute("newCustomer") Customer customer) {
        if (customer.getCustomerId() != null) customerService.updateCustomer(customer.getCustomerId(), customer);
        else customerService.createCustomer(customer);
        return "redirect:/web/customers";
    }

    @GetMapping("/web/customers/delete/{id}")
    public String deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
        return "redirect:/web/customers";
    }

    @GetMapping("/web/rooms")
    public String viewRoomsPage(Model model) {
        model.addAttribute("listRooms", roomService.getAllRooms());
        model.addAttribute("newRoom", new Room());
        return "rooms";
    }

    @PostMapping("/web/rooms/save")
    public String saveRoom(@ModelAttribute("newRoom") Room room) {
        roomService.createRoom(room);
        return "redirect:/web/rooms";
    }

    @GetMapping("/web/rooms/delete/{id}")
    public String deleteRoom(@PathVariable Integer id) {
        roomService.deleteRoom(id);
        return "redirect:/web/rooms";
    }

    @GetMapping("/web/reservations")
    public String viewReservationsPage(Model model) {
        model.addAttribute("listReservations", reservationService.getAllReservations());
        return "reservations";
    }

    @GetMapping("/web/reservations/cancel/{id}")
    public String cancelReservation(@PathVariable Integer id) {
        reservationService.cancelReservation(id);
        return "redirect:/web/reservations";
    }
}