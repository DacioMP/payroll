package com.pedrosa.demo.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pedrosa.demo.services.exceptions.OrderNotFoundException;

@RestControllerAdvice
public class OrderNotFoundAdvice {

  @ExceptionHandler(OrderNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String employeeNotFoundHandler(OrderNotFoundException ex) {
    return ex.getMessage();
  }
}
