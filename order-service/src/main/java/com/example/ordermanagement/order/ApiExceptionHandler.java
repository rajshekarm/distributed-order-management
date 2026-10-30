package com.example.ordermanagement.order;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(OrderService.OrderNotFoundException.class)
    Map<String, String> notFound(RuntimeException e) { return Map.of("error", e.getMessage()); }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    Map<String, String> badRequest(IllegalArgumentException e) { return Map.of("error", e.getMessage()); }
}
