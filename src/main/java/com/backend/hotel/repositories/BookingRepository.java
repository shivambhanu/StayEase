package com.backend.hotel.repositories;

import com.backend.hotel.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookingRepository extends JpaRepository<Booking, Long> {
}
