package com.telegram.bot.controller;

import com.telegram.bot.service.exception.InvalidIdException;
import com.telegram.bot.service.exception.InvalidNameException;
import com.telegram.bot.service.exception.IsExistException;
import com.telegram.bot.service.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        HashMap<String, String> map = new HashMap<>();
        for (FieldError fieldError : ex.getFieldErrors()) {
            map.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        HashMap<String, String> map = new HashMap<>();
        map.put("Your method", ex.getMethod());
        for (HttpMethod supportedHttpMethod : ex.getSupportedHttpMethods()) {
            map.put("Supported method", supportedHttpMethod.name());
        }
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }


    /**
     * Method for handling NotFoundException.
     * @throws NotFoundException if the object is not found.
     * @return the ResponseEntity object with a specific status.
     * */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValid(NotFoundException m){
        HashMap<String, String> map = new HashMap<>();
        map.put("message", m.getMessage());
        map.put("method", m.getMethod());
        map.put("value", String.valueOf(m.getValue()));
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }


    /**
     * Method for handling IsExistException.
     * @throws IsExistException if the object is exist.
     * @return the ResponseEntity object with a specific status.
     * */
    @ExceptionHandler(IsExistException.class)
    public ResponseEntity<Map<String, String>> userIsExist(IsExistException u){
        HashMap<String, String> map = new HashMap<>();
        map.put("message", u.getMessage());
        map.put("method", u.getMethod());
        map.put("value", u.getValue());
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }


    /**
     * Method for handling InvalidIdException.
     * @throws InvalidIdException if id is wrong.
     * @return the ResponseEntity object with a specific status.
     * */
    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<Map<String, String>> invalidId(InvalidIdException exception){
        HashMap<String, String> map = new HashMap<>();
        map.put("message", exception.getMessage());
        map.put("method", exception.getMethod());
        map.put("value", String.valueOf(exception.getId()));
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }


    /**
     * Method for handling InvalidNameException.
     * @throws InvalidNameException if a name is wrong.
     * @return the ResponseEntity object with a specific status.
     * */
    @ExceptionHandler(InvalidNameException.class)
    public ResponseEntity<Map<String, String>> invalidTitle(InvalidNameException exception){
        HashMap<String, String> map = new HashMap<>();
        map.put("message", exception.getMessage());
        map.put("method", exception.getMethod());
        map.put("value", exception.getName());
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }
}
