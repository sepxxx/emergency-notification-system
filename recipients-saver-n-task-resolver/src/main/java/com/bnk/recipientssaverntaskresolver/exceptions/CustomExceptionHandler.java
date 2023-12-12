package com.bnk.recipientssaverntaskresolver.exceptions;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Log4j2
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> exception(Exception ex, WebRequest request) throws Exception {
//
//        log.error("Exception during execution of application", ex);
//
//        return handleException(ex, request);
//    }
    @ExceptionHandler(ForbiddenException.class)
    protected ResponseEntity<String> handleForbiddenException() {
        return new ResponseEntity<>("FORBIDDEN FOR ACTION", HttpStatus.FORBIDDEN);
    }
    //TODO: здесь добавить сообщения из exceptions
    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<String> handleNotFoundException(Exception ex) {
        return new ResponseEntity<>("NotFoundException", HttpStatus.NOT_FOUND);
    }
}