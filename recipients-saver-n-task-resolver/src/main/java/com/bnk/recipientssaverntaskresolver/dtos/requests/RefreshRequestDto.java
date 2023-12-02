package com.bnk.recipientssaverntaskresolver.dtos.requests;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RefreshRequestDto {
    public String refreshToken;
}