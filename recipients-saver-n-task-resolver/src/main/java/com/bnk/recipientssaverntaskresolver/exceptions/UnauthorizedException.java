package com.bnk.recipientssaverntaskresolver.exceptions;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@AllArgsConstructor
public class UnauthorizedException extends RuntimeException{
    String message;
//    public UnauthorizedException(String message) {
//        super(message);
//    }
}