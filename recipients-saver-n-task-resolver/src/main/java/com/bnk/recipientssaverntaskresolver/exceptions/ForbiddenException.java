package com.bnk.recipientssaverntaskresolver.exceptions;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ForbiddenException extends RuntimeException {
    String message;
}
