package com.example.spring_member.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.spring_member.exception.MemberNotFoundException; 
import com.example.spring_member.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException e) {

        String message = e.getBindingResult()
                .getFieldError()
                .getDefaultMessage();

        ErrorResponse errorResponse = new ErrorResponse(400, message);

        return ResponseEntity.badRequest().body(errorResponse);
    }
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMemberNotFound(
        MemberNotFoundException e) {

         ErrorResponse errorResponse =
            new ErrorResponse(404, e.getMessage());

        return ResponseEntity.status(404)
            .body(errorResponse);
}
}