package com.backend.hotel.config;

import com.backend.hotel.services.JWTService;
import com.backend.hotel.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserService userService;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userName;

        //Check authentication header
        if(authHeader == null || !authHeader.startsWith(("Bearer "))){
            filterChain.doFilter(request, response);
            return;
        }

        //Extract the token from the header
        jwt = authHeader.substring(7);

        //Extract the username from the token(username is the email)
        userName = jwtService.extractUserName(jwt);

        //Authenticate if not already authenticated
        if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails user = userService.loadUserByUsername(userName);

            //Check if token is valid
            if(jwtService.isTokenValid(jwt, user)){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                //Set additional details such as user's IP address, browser, or other attributes
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        //call the next filter
        filterChain.doFilter(request, response);
    }
}
