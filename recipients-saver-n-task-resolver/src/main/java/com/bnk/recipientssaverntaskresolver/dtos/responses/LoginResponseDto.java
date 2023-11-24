package com.bnk.recipientssaverntaskresolver.dtos.responses;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)


public class LoginResponseDto {
    Long id;
    String firstName;
    String lastName;
    String role;
    String accessToken;
    String refreshToken;
}
