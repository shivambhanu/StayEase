package com.backend.hotel.services;

import com.backend.hotel.controllers.exchanges.request.RegisterRequest;
import com.backend.hotel.controllers.exchanges.response.AuthResponse;
import com.backend.hotel.models.User;
import com.backend.hotel.models.enums.Role;
import com.backend.hotel.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JWTService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;


    public AuthResponse register(RegisterRequest request){
        if(request.getRole() == null) {
            request.setRole(Role.CUSTOMER);
        }

        User user = User.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
//        userRepository.save(user);  //Already saving the user above

        return AuthResponse.builder()
                .accessToken(jwtToken)
                .build();
    }
}
