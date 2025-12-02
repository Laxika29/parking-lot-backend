package com.gniot.parkinglot.configuration;

import com.gniot.parkinglot.dto.response.ErrorResponse;
import com.gniot.parkinglot.exception.ParkingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    private ErrorResponse buildError(HttpStatus status, String message, HttpServletRequest request) {
        return new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI()
        );
    }

    private ErrorResponse customBuildError(int status, String error, String message, HttpServletRequest request) {
        return new ErrorResponse(
                LocalDateTime.now(),
                status,
                error,
                message,
                request.getRequestURI()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        String errorMsg = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .findFirst()
                .orElse("Invalid request");

        ErrorResponse error = buildError(HttpStatus.BAD_REQUEST, errorMsg, request);
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        ErrorResponse error = buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(SecurityException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handleSecurityException(SecurityException ex, HttpServletRequest request) {
        ErrorResponse error = buildError(HttpStatus.UNAUTHORIZED, ex.getMessage(), request);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        ErrorResponse error = buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ParkingException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ParkingException ex, HttpServletRequest request) {
        ErrorResponse error;
        if (ex.getStatus() == "SUCCESS") {
             error = customBuildError(0, null, ex.getMessage(), request);
        } else {
            error = customBuildError(1, ex.getMessage(), ex.getMessage(), request);
        }
        return ResponseEntity.status(HttpStatus.OK).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex, HttpServletRequest request) {

        ex.printStackTrace(); // for debugging (remove in production)

        ErrorResponse error = buildError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Something went wrong. Please try again.",
                request
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
