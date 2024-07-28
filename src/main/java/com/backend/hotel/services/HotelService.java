package com.backend.hotel.services;

import com.backend.hotel.models.Hotel;
import com.backend.hotel.repositories.HotelRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    public List<Hotel> getAllHotels(){
        return hotelRepository.findAll();
    }

    public Hotel getHotelById(Long hotelId){
        return hotelRepository.findById(hotelId).orElseThrow(() -> new EntityNotFoundException("hotel with given id not found!"));
    }


    @PreAuthorize("hasAuthority('HOTEL_MANAGER')")
    public Hotel updateHotel(Long hotelId, Hotel newHotel){
        Hotel currHotel = hotelRepository.findById(hotelId).orElseThrow(() -> new EntityNotFoundException("hotel with given id not found!"));

        currHotel.setHotelName(newHotel.getHotelName());
        currHotel.setLocation(newHotel.getLocation());
        currHotel.setDescription(newHotel.getDescription());
        currHotel.setTotalAvailableRooms(newHotel.getTotalAvailableRooms());

        return hotelRepository.save(currHotel);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    public Hotel saveHotel(Hotel hotel){
        return hotelRepository.save(hotel);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void removeHotel(Long hotelId){
        hotelRepository.deleteById(hotelId);
    }
}
