package com.demo.first.app.exceptions;

import com.demo.first.app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    //Exception Handling Method
    //poori application mai jaha bhi yeh illegal arg exception aaye, tab yeh method
    //trigger ho uske liye exception handler wali annotation add ki gyi hai
    //simple - we have mapped this method with illegalArg exception class
    // so it will execute only for illegalArg exception wherever it is thrown
    //if want it ot handle multiple then add it in curly braces
    @ExceptionHandler({UserNotFoundException.class,IllegalArgumentException.class, NullPointerException.class})
    public ResponseEntity<Map<String,Object>> handleIllegalArgumentException(
            //we are receiving this object through exception in updateUser in serviceController file
            //  IllegalArgumentException exception ->since 2 exception class present so use Generic "Exception" class for object which is actually a parent class
            Exception exception
    ){
        Map<String,Object> errorResponse=new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
        errorResponse.put("error","Bad request");
        errorResponse.put("message",exception.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);

    }
    //this is in built exception thrown but we want to give it a custom reponse
    //so we are basically catching it here and sending our custom response
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String,Object>> handleMethodNotSupported(
            //we are receiving this object through exception in updateUser in servieController file
            //  IllegalArgumentException exception ->since 2 exception class present so use Generic "Exception" class for object which is actually a parent class
            Exception exception
    ){
        logger.error("Error when finding user : ",exception);
        Map<String,Object> errorResponse=new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", HttpStatus.METHOD_NOT_ALLOWED.value());
        errorResponse.put("error","method not allowed on this endpoint");
        errorResponse.put("message",exception.getMessage());
        return new ResponseEntity<>(errorResponse,HttpStatus.METHOD_NOT_ALLOWED);

    }
}
