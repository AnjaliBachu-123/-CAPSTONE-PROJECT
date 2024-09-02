package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.entity.Booking;
import com.entity.Customer;
import com.entity.Room;
import com.service.CustomerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;
    
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Customer customer) {
        try {
            logger.info("Attempting to register customer: {}", customer.getCustomerName());
            Customer registeredCustomer = customerService.registerCustomer(customer);
            logger.info("Registration successful for customer: {}", registeredCustomer.getCustomerName());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Registration successful. Welcome, " + registeredCustomer.getCustomerName() + "!");
        } catch (IllegalArgumentException e) {
            logger.error("Registration failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (Exception e) {
            logger.error("An error occurred during registration: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred");
        }
    }

//    @PostMapping("/register")
//    public ResponseEntity<String> register(@RequestBody Customer customer) {
//        logger.info("Attempting to register customer: {}", customer.getCustomerName());
//        Customer registeredCustomer = customerService.registerCustomer(customer);
//        logger.info("Registration successful for customer: {}", registeredCustomer.getCustomerName());
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body("Registration successful. Welcome, " + registeredCustomer.getCustomerName() + "!");
//    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        logger.info("Attempting to log in customer with email: {}", email);
        Customer customer = customerService.login(email, password);
        logger.info("Login successful for customer: {}", customer.getCustomerName());
        return ResponseEntity.ok("Login successful. Welcome back, " + customer.getCustomerName() + "!");
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<Room>> viewRooms(@RequestParam String location) {
        logger.info("Fetching rooms for location: {}", location);
        List<Room> rooms = customerService.viewRooms(location);
        logger.info("Found {} rooms for location: {}", rooms.size(), location);
        return ResponseEntity.ok(rooms);
    }

    @PostMapping("/book")
    public ResponseEntity<String> bookRoom(@RequestParam int customerId, @RequestParam int roomId) {
        logger.info("Attempting to book room with ID: {} for customer ID: {}", roomId, customerId);
        Booking booking = customerService.bookRoom(customerId, roomId);
        logger.info("Booking confirmed with ID: {}", booking.getBookingId());
        return ResponseEntity.ok("Booking confirmed! Booking ID: " + booking.getBookingId() 
                + ", Room ID: " + booking.getRoomId() + ", Status: " + booking.getStatus());
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> viewBookings(@RequestParam int customerId) {
        logger.info("Fetching bookings for customer ID: {}", customerId);
        List<Booking> bookings = customerService.viewBookings(customerId);
        logger.info("Found {} bookings for customer ID: {}", bookings.size(), customerId);
        return ResponseEntity.ok(bookings);
    }
}
//package com.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.entity.Booking;
//import com.entity.Customer;
//import com.entity.Room;
//import com.service.CustomerService;
//
//import jakarta.validation.Valid;
//
//@RestController
//@RequestMapping("/customers")
//public class CustomerController {
//    
//    @Autowired
//    private CustomerService customerService;
//
//    @PostMapping("/register")
//    public ResponseEntity<Customer> register(@RequestBody @Valid Customer customer) {
//        Customer registeredCustomer = customerService.registerCustomer(customer);
//        return ResponseEntity.ok(registeredCustomer);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<Customer> login(@RequestParam String email, @RequestParam String password) {
//        Customer customer = customerService.login(email, password);
//        return ResponseEntity.ok(customer);
//    }
//
//    @GetMapping("/rooms")
//    public ResponseEntity<List<Room>> viewRooms(@RequestParam String location) {
//        List<Room> rooms = customerService.viewRooms(location);
//        return ResponseEntity.ok(rooms);
//    }
//
//    @PostMapping("/book")
//    public ResponseEntity<Booking> bookRoom(@RequestParam int customerId, @RequestParam int roomId) {
//        Booking booking = customerService.bookRoom(customerId, roomId);
//        return ResponseEntity.ok(booking);
//    }
//
//    @GetMapping("/bookings")
//    public ResponseEntity<List<Booking>> viewBookings(@RequestParam int customerId) {
//        List<Booking> bookings = customerService.viewBookings(customerId);
//        return ResponseEntity.ok(bookings);
//    }
//}
