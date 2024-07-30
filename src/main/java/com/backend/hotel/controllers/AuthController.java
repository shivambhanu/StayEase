package com.backend.hotel.controllers;

import com.backend.hotel.controllers.exchanges.request.AuthRequest;
import com.backend.hotel.controllers.exchanges.request.RegisterRequest;
import com.backend.hotel.controllers.exchanges.response.AuthResponse;
import com.backend.hotel.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request){
        return ResponseEntity.ok(authService.login(request));
    }



    //Testing basic directories for different roles
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
