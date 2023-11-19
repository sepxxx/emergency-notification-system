package com.bnk.recipientssaverntaskresolver.controllers;


import com.bnk.miscellaneous.entities.User;
import com.bnk.recipientssaverntaskresolver.dtos.AuthRequestDto;
import com.bnk.recipientssaverntaskresolver.dtos.UserRegistrationRequestDto;
import com.bnk.recipientssaverntaskresolver.services.JwtService;
import com.bnk.recipientssaverntaskresolver.services.UserDetailsImpl;
import com.bnk.recipientssaverntaskresolver.services.UserDetailsServiceImpl;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserDetailsServiceImpl userDetailsServiceImpl;
    JwtService jwtService;
    AuthenticationManager authenticationManager;

    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody UserRegistrationRequestDto userRegistrationRequestDto) {
        return userDetailsServiceImpl.addUser(new User(
                userRegistrationRequestDto.getUsername(),
                userRegistrationRequestDto.getPassword(),
//                userRegistrationRequestDto.getRoles()
                "ROLE_USER"
        ));
    }
//    @GetMapping("/user/userProfile")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public String userProfile() {
//        return "Welcome to User Profile";
//    }
//
//    @GetMapping("/admin/adminProfile")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    public String adminProfile() {
//        return "Welcome to Admin Profile";
//    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequestDto authRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(),
                        authRequestDto.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequestDto.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }
}
