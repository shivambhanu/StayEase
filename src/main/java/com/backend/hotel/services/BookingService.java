package com.backend.hotel.services;

import com.backend.hotel.models.Booking;
import com.backend.hotel.models.Hotel;
import com.backend.hotel.models.User;
import com.backend.hotel.repositories.BookingRepository;
import com.backend.hotel.repositories.HotelRepository;
import com.backend.hotel.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    HotelRepository hotelRepository;


    @PreAuthorize("hasAuthority('HOTEL_MANAGER') or hasAuthority('ADMIN')")
    public List<Booking> getAllBookings(){
        return bookingRepository.findAll();
    }

//    @PreAuthorize("hasAuthority('HOTEL_MANAGER') or hasAuthority('ADMIN')")
//    public Booking getBookingById(Long bookingId){
//        return bookingRepository.findById(bookingId).orElseThrow(() -> new EntityNotFoundException("Booking not found for the given id"));
//    }


    //Customers must be able to book rooms using the service
    public Booking bookHotel(Long hotelId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userObj = (User) authentication.getPrincipal();

        Long userId = userObj.getUserId();
        User currUser = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found with given id"));

        Hotel currHotel = hotelRepository.findById(hotelId).orElseThrow(() -> new EntityNotFoundException("Hotel not found with given id"));

        //check if there are rooms available
        int currTotalRoomsAvailable = currHotel.getTotalAvailableRooms();
        if(currTotalRoomsAvailable > 0){
            throw new IllegalStateException("Rooms not available in this hotel");
        }

        Booking newBooking = new Booking();
        newBooking.setUser(currUser);
        newBooking.setHotel(currHotel);
        newBooking.setBookingDate(LocalDate.now());
        newBooking.setBookingStatus(true);

        //decrease the number of available rooms in the selected hotel
        //only one room can be booked in a single request
        currHotel.setTotalAvailableRooms(currTotalRoomsAvailable-1);
        hotelRepository.save(currHotel);

        return bookingRepository.save(newBooking);
    }


    @PreAuthorize("hasAuthority('HOTEL_MANAGER') or hasAuthority('ADMIN')")
    public void cancelBooking(Long bookingId){
        Booking currBooking = bookingRepository.findById(bookingId).orElseThrow(() -> new EntityNotFoundException("Booking not found with given id"));

        Hotel currHotel = hotelRepository.findById(currBooking.getHotel().getHotelId()).orElseThrow(() -> new EntityNotFoundException("Hotel not found with given id"));

        //Throw exception if the booking was already cancelled earlier.
        if(!currBooking.isBookingStatus()){
            throw new IllegalStateException("This booking has already been cancelled!");
        }

        //Free the room that was booked
        int currTotalRoomsAvailable = currHotel.getTotalAvailableRooms();
        currHotel.setTotalAvailableRooms(currTotalRoomsAvailable+1);
        hotelRepository.save(currHotel);

        //set the checkOutDate for this booking
        currBooking.setBookingStatus(false);
        bookingRepository.save(currBooking);
    }
}
