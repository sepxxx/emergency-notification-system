package com.bnk.recipientssaverntaskresolver.controllers;


import com.bnk.miscellaneous.entities.User;
import com.bnk.recipientssaverntaskresolver.dtos.requests.AuthRequestDto;
import com.bnk.recipientssaverntaskresolver.dtos.requests.RefreshRequestDto;
import com.bnk.recipientssaverntaskresolver.dtos.requests.UserRegistrationRequestDto;
import com.bnk.recipientssaverntaskresolver.dtos.responses.JwtResponseDto;
import com.bnk.recipientssaverntaskresolver.dtos.responses.LoginResponseDto;
import com.bnk.recipientssaverntaskresolver.dtos.responses.UserDto;
import com.bnk.recipientssaverntaskresolver.services.JwtService;
import com.bnk.recipientssaverntaskresolver.services.UserDetailsServiceImpl;
import com.bnk.recipientssaverntaskresolver.services.UserService;
import jakarta.security.auth.message.AuthException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
//@RequestMapping("/auth")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
//@CrossOrigin(origins = {"http://localhost:4200/admin", "http://localhost:4200/"}, maxAge = 3600)
public class UserController {
    UserDetailsServiceImpl userDetailsServiceImpl;
    JwtService jwtService;
    AuthenticationManager authenticationManager;
    UserService userService;

    @PostMapping("/auth/register")
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

    @PostMapping("/auth/login")
    public LoginResponseDto authenticateAndGetTokens(@RequestBody AuthRequestDto authRequestDto) {
        log.info("authenticateAndGetTokens authRequestDto: {}", authRequestDto);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDto.getUsername(),
                        authRequestDto.getPassword()));
        if (authentication.isAuthenticated()) {
            String atoken = jwtService.generateAccessToken(authRequestDto.getUsername());
            String rtoken = jwtService.generateRefreshToken(authRequestDto.getUsername());
            jwtService.putToRefreshStorage(authRequestDto.getUsername(), rtoken);

            String role = "User";
            if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
                role = "Admin";
            return new LoginResponseDto(0L,"test", "test", role, atoken, rtoken);

        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

    @PostMapping("/auth/token")
    public String getAccessTokenByRefresh(@RequestBody RefreshRequestDto refreshRequestDto)  {
        log.info("getAccessTokenByRefresh RefreshRequestDto.getRefreshToken: {}", refreshRequestDto.getRefreshToken());
        return jwtService.refreshAccessToken(refreshRequestDto.getRefreshToken());
    }

    @GetMapping("/users")
//    @PreAuthorize("ROLE_USER")
    public Set<UserDto> getAll() {
        return userService.getAll();
    }

}
