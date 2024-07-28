package com.backend.hotel.controllers;

import com.backend.hotel.models.Booking;
import com.backend.hotel.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookingController {
    @Autowired
    BookingService bookingService;


    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getAllBookings(){
        return ResponseEntity.ok(bookingService.getAllBookings());
    }


    @PostMapping("/hotels/{hotelId}/book")
    public ResponseEntity<Booking> bookHotel(@PathVariable Long hotelId){
        return new ResponseEntity<>(bookingService.bookHotel(hotelId), HttpStatus.CREATED);
    }


    @DeleteMapping("/bookings/{bookingId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId){
        bookingService.cancelBooking(bookingId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
