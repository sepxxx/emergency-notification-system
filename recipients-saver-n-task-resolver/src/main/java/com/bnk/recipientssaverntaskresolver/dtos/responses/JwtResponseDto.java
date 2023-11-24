package com.bnk.recipientssaverntaskresolver.dtos.responses;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtResponseDto {
     String accessToken;
     String refreshToken;
}
