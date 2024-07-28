package com.backend.hotel.controllers;

import com.backend.hotel.models.Hotel;
import com.backend.hotel.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HotelController {
    @Autowired
    HotelService hotelService;


    @GetMapping("/hotels")
    public ResponseEntity<List<Hotel>> getAllHotels(){
        return ResponseEntity.ok(hotelService.getAllHotels());
    }

    @GetMapping("/hotels/{hotelId}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable Long hotelId){
        return ResponseEntity.ok(hotelService.getHotelById(hotelId));
    }


    @PostMapping("/hotels")
    public ResponseEntity<Hotel> saveHotel(@RequestBody Hotel hotel){
        return new ResponseEntity<>(hotelService.saveHotel(hotel), HttpStatus.CREATED);
    }

    @PutMapping("/hotels/{hotelId}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable Long hotelId, @RequestBody Hotel newHotel){
        return ResponseEntity.ok(hotelService.updateHotel(hotelId, newHotel));
    }

    @DeleteMapping("hotels/{hotelId}")
    public ResponseEntity<Void> removeHotel(@PathVariable Long hotelId){
        hotelService.removeHotel(hotelId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
