package com.codeki.flightsapi.exceptions;

import com.codeki.flightsapi.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionConfig {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseDto> handleNoElementException(NotFoundException e) {
        ResponseDto responseDto = new ResponseDto(e.getMessage());
        return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
    }
}