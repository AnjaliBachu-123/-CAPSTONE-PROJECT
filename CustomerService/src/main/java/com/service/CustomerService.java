package com.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.entity.Booking;
import com.entity.Customer;
import com.entity.Room;
import com.repository.BookingRepository;
import com.repository.CustomerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RestTemplate restTemplate;
    
    public Customer register(Customer customer) {
        logger.debug("Validating customer registration data");

        // Initialize a StringBuilder to collect error messages
        StringBuilder errorMessages = new StringBuilder();

        // Check for each parameter and append missing ones to errorMessages
        if (customer.getCustomerName() == null || customer.getCustomerName().isEmpty()) {
            errorMessages.append("Customer name is required. ");
            logger.error("Customer name is missing");
        }
        if (customer.getEmail() == null || customer.getEmail().isEmpty()) {
            errorMessages.append("Email is required. ");
            logger.error("Email is missing");
        }
        if (customer.getPassword() == null || customer.getPassword().isEmpty()) {
            errorMessages.append("Password is required. ");
            logger.error("Password is missing");
        }
        if (customer.getDateOfBirth() == null) {
            errorMessages.append("Date of birth is required. ");
            logger.error("Date of birth is missing");
        }

        // If there are error messages, throw an exception with all the messages
        if (errorMessages.length() > 0) {
            String errorMessage = errorMessages.toString().trim();
            logger.error("Registration failed: {}", errorMessage);
            throw new IllegalArgumentException(errorMessage);
        }

        logger.info("Registering customer: {}", customer.getCustomerName());
        return customerRepository.save(customer);
    }
    
//    public Customer registerCustomer(Customer customer) {
//        logger.debug("Validating customer registration data");
//        
//        // Check if the email already exists
//        if (customerRepository.findByEmail(customer.getEmail()).isPresent()) {
//            logger.error("Email already exists: {}", customer.getEmail());
//            throw new IllegalArgumentException("Email already exists");
//        }
//
//        // Validate required fields
//        if (customer.getCustomerName() == null || customer.getCustomerName().isEmpty()) {
//            logger.error("Customer name is missing");
//            throw new IllegalArgumentException("Customer name is required");
//        }
//        if (customer.getEmail() == null || customer.getEmail().isEmpty()) {
//            logger.error("Email is missing");
//            throw new IllegalArgumentException("Email is required");
//        }
//        if (customer.getPassword() == null || customer.getPassword().isEmpty()) {
//            logger.error("Password is missing");
//            throw new IllegalArgumentException("Password is required");
//        }
//        if (customer.getDateOfBirth() == null) {
//            logger.error("Date of birth is missing");
//            throw new IllegalArgumentException("Date of birth is required");
//        }
//
//        logger.info("Registering customer: {}", customer.getCustomerName());
//        return customerRepository.save(customer);
//    }

    public Customer registerCustomer(Customer customer) {
        logger.debug("Validating customer registration data");
        if (customer.getCustomerName() == null || customer.getCustomerName().isEmpty()) {
            logger.error("Customer name is missing");
            throw new IllegalArgumentException("Customer name is required");
        }
        if (customer.getEmail() == null || customer.getEmail().isEmpty()) {
            logger.error("Email is missing");
            throw new IllegalArgumentException("Email is required");
        }
        if (customer.getPassword() == null || customer.getPassword().isEmpty()) {
            logger.error("Password is missing");
            throw new IllegalArgumentException("Password is required");
        }
        if (customer.getDateOfBirth() == null) {
            logger.error("Date of birth is missing");
            throw new IllegalArgumentException("Date of birth is required");
        }
        logger.info("Registering customer: {}", customer.getCustomerName());
        return customerRepository.save(customer);
    }

    public Customer login(String email, String password) {
        logger.info("Logging in customer with email: {}", email);
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.error("Invalid credentials for email: {}", email);
                    return new RuntimeException("Invalid credentials");
                });
        if (!customer.getPassword().equals(password)) {
            logger.error("Password mismatch for customer: {}", email);
            throw new RuntimeException("Invalid credentials");
        }
        logger.info("Login successful for customer: {}", customer.getCustomerName());
        return customer;
    }

    public List<Room> viewRooms(String location) {
        logger.info("Fetching rooms from location: {}", location);
        if (location == null || location.isEmpty()) {
            logger.error("Location is missing");
            throw new IllegalArgumentException("Location is required");
        }
        String url = "http://localhost:8082/rooms/location/" + location;
        ResponseEntity<List<Room>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Room>>() {
                });

        List<Room> rooms = response.getBody();
        if (rooms == null || rooms.isEmpty()) {
            logger.warn("No rooms found for location: {}", location);
            throw new RuntimeException("No rooms available at the specified location");
        }

        logger.info("Found {} rooms at location: {}", rooms.size(), location);
        return rooms;
    }

    public Booking bookRoom(int customerId, int roomId) {
        logger.info("Attempting to book room with ID: {} for customer ID: {}", roomId, customerId);
        if (customerId <= 0) {
            logger.error("Invalid Customer ID: {}", customerId);
            throw new IllegalArgumentException("Customer ID must be greater than zero");
        }

        String url = "http://localhost:8082/rooms/" + roomId;
        Room room = restTemplate.getForObject(url, Room.class);

        if (room == null || !"Available".equals(room.getAvailabilityStatus())) {
            logger.warn("Room ID: {} is not available", roomId);
            throw new RuntimeException("Room not available");
        }

        Booking booking = new Booking();
        booking.setCustomerId(customerId);
        booking.setRoomId(roomId);
        booking.setBookingDate(new Date(System.currentTimeMillis()));
        booking.setStatus("Booked");

        logger.info("Saving booking for Room ID: {} with Customer ID: {}", roomId, customerId);
        return bookingRepository.save(booking);
    }

    public List<Booking> viewBookings(int customerId) {
        logger.info("Fetching bookings for customer ID: {}", customerId);
        if (customerId <= 0) {
            logger.error("Invalid Customer ID: {}", customerId);
            throw new IllegalArgumentException("Customer ID must be greater than zero");
        }

        List<Booking> bookings = bookingRepository.findByCustomerId(customerId);
        logger.info("Found {} bookings for customer ID: {}", bookings.size(), customerId);
        return bookings;
    }
}
//package com.service;
//
//
//import java.util.Date;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import com.entity.Booking;
//import com.entity.Customer;
//import com.entity.Room;
//import com.repository.BookingRepository;
//import com.repository.CustomerRepository;
//
//@Service
//public class CustomerService {
//    
//    @Autowired
//    private CustomerRepository customerRepository;
//    
//    @Autowired
//    private BookingRepository bookingRepository;
//    
//    @Autowired
//    private RestTemplate restTemplate;
//
//    public Customer registerCustomer(Customer customer) {
//        return customerRepository.save(customer);
//    }
//
//    public Customer login(String email, String password) {
//        Customer customer = customerRepository.findByEmail(email)
//            .orElseThrow(() -> new RuntimeException("Invalid credentials"));
//        if (!customer.getPassword().equals(password)) {
//            throw new RuntimeException("Invalid credentials");
//        }
//        return customer;
//    }
//
//    public List<Room> viewRooms(String location) {
//        String url = "http://localhost:8082/rooms/location/" + location;
//        ResponseEntity<List<Room>> response = restTemplate.exchange(
//            url,
//            HttpMethod.GET,
//            null,
//            new ParameterizedTypeReference<List<Room>>() {}
//        );
//        return response.getBody();
//    }
//
//    public Booking bookRoom(int customerId, int roomId) {
//        // Check room availability first
//        String url = "http://localhost:8082/rooms/" + roomId;
//        Room room = restTemplate.getForObject(url, Room.class);
//        
//        if (room == null || !"Available".equals(room.getAvailabilityStatus())) {
//            throw new RuntimeException("Room not available");
//        }
//
//        Booking booking = new Booking();
//        booking.setCustomerId(customerId);
//        booking.setRoomId(roomId);
//        booking.setBookingDate(new Date());
//        booking.setStatus("Booked");
//
//        return bookingRepository.save(booking);
//    }
//
//    public List<Booking> viewBookings(int customerId) {
//        return bookingRepository.findByCustomerId(customerId);
//    }
//}
