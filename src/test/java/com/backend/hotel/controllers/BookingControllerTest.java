package com.backend.hotel.controllers;

import com.backend.hotel.models.Booking;
import com.backend.hotel.models.Hotel;
import com.backend.hotel.models.User;
import com.backend.hotel.models.enums.Role;
import com.backend.hotel.services.BookingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BookingService bookingServiceMock;


    @Test
    @WithMockUser(username = "customer@email.com", roles = "CUSTOMER")
    public void testBookHotel() throws Exception {
        //Arrange
        Long userId = 1L, hotelId = 1L, bookingId = 1L;
        User currUser = new User(userId, "customer@email.com", "customer", "user", "customerPass", Role.CUSTOMER);
        Hotel currHotel = new Hotel(hotelId, "Grand Palace", "California/USA", "Good hotel", 230);
        Booking currBooking = new Booking(bookingId,  currUser, currHotel, LocalDate.now(), null, true);

        when(bookingServiceMock.bookHotel(hotelId)).thenReturn(currBooking);

        //Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/hotels/{hotelId}/book", hotelId))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookingId").value(bookingId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.userId").value(userId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.email").value("customer@email.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.hotel.hotelId").value(hotelId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.hotel.hotelName").value("Grand Palace"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.hotel.totalAvailableRooms").value(230))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookingStatus").value(currBooking.isBookingStatus()));
    }


    @Test
    @WithMockUser(username = "manager@email.com", roles = "HOTEL_MANAGER")
    public void testCancelBooking() throws Exception {
        Mockito.doNothing().when(bookingServiceMock).cancelBooking(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/bookings/{bookingId}", anyLong()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(bookingServiceMock, times(1)).cancelBooking(0L);
    }
}
