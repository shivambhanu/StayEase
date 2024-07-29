package com.backend.hotel.services;

import com.backend.hotel.models.Booking;
import com.backend.hotel.models.Hotel;
import com.backend.hotel.models.User;
import com.backend.hotel.models.enums.Role;
import com.backend.hotel.repositories.BookingRepository;
import com.backend.hotel.repositories.HotelRepository;
import com.backend.hotel.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {
    @Mock
    BookingRepository bookingRepositoryMock;

    @Mock
    UserRepository userRepositoryMock;

    @Mock
    HotelRepository hotelRepositoryMock;

        @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private BookingService bookingServiceMock;


    @Test
    public void testBookHotel(){
        //Arrange
        //Arrange
        Long userId = 1L, hotelId = 1L, bookingId = 1L;
        User currUser = new User(userId, "customer@email.com", "customer", "user", "customerPass", Role.CUSTOMER);
        Hotel currHotel = new Hotel(hotelId, "Grand Palace", "California/USA", "Good hotel", 230);
//        Booking currBooking = new Booking(bookingId,  currUser, currHotel, LocalDate.now(), null, true);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(currUser);
        SecurityContextHolder.setContext((securityContext));

        when(userRepositoryMock.findById(anyLong())).thenReturn(Optional.of(currUser));
        when(hotelRepositoryMock.findById(anyLong())).thenReturn(Optional.of(currHotel));
        when(bookingRepositoryMock.save(any(Booking.class))).thenReturn(any(Booking.class));

        Booking actualBooking = bookingServiceMock.bookHotel(anyLong());

    }



    @Test
    public void testCancelBooking(){

    }
}
