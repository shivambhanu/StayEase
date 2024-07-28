package com.backend.hotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class HotelApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelApplication.class, args);
	}


	@GetMapping("/")
	public String welcome() {
		return "HELLO FROM AUTHENTICATED ENDPOINT!";
	}

	@GetMapping("/manager")
	@PreAuthorize("hasAuthority('HOTEL_MANAGER') or hasAuthority('ADMIN')")
	public String welcomeManager() {
		return "HELLO FROM HOTEL_MANAGER'S ENDPOINT!";
	}

	@GetMapping("/admins")
	@PreAuthorize("hasAuthority('ADMIN')")
	public String welcomeAdmin() {
		return "HELLO FROM ADMIN'S ENDPOINT!";
	}
}
