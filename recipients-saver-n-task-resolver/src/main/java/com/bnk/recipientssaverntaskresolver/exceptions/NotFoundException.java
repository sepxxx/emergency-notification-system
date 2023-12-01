package com.bnk.recipientssaverntaskresolver.exceptions;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(code = HttpStatus.NOT_FOUND)

@Data
@AllArgsConstructor
public class NotFoundException extends RuntimeException{
    String message;
//    public NotFoundException(String message) {
//        super(message);
//    }
}
