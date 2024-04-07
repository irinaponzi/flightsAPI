package com.codeki.flightsapi.exceptions;

import com.codeki.flightsapi.dto.RespErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionConfig {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<RespErrorDto> handleNoElementException(ObjectNotFoundException e) {
        RespErrorDto respErrorDto = new RespErrorDto(e.getMessage());
        return new ResponseEntity<>(respErrorDto, HttpStatus.NOT_FOUND);
    }
}