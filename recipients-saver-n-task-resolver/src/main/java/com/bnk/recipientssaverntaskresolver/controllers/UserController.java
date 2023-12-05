package com.bnk.recipientssaverntaskresolver.controllers;


import com.bnk.miscellaneous.entities.User;
import com.bnk.recipientssaverntaskresolver.dtos.requests.AuthRequestDto;
import com.bnk.recipientssaverntaskresolver.dtos.requests.UserRegistrationRequestDto;
import com.bnk.recipientssaverntaskresolver.dtos.responses.LoginResponseDto;
import com.bnk.recipientssaverntaskresolver.jwt.JwtService;
import com.bnk.recipientssaverntaskresolver.jwt.UserDetailsServiceImpl;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    UserDetailsServiceImpl userDetailsServiceImpl;
    JwtService jwtService;
    AuthenticationManager authenticationManager;

    @PostMapping("/register")
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


    //TODO: логику вынести из контроллера
    @PostMapping("/login")
    public LoginResponseDto authenticate(@RequestBody AuthRequestDto authRequestDto) {
        log.info(" authenticate authRequestDto: {} ", authRequestDto);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(),
                        authRequestDto.getPassword()));
        if (authentication.isAuthenticated()) {
            String role = "ROLE_USER";
            //TODO: не очень хорошая проверка, странно что нужно каждый раз создавать SGA
            if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
                role = "ROLE_ADMIN";
            String jwt = jwtService.generateToken(authRequestDto.getUsername());
            return new LoginResponseDto(authRequestDto.getUsername(), role, jwt);
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }
}
