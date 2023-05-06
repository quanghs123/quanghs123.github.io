package com.example.backend.handler;

import com.example.backend.exception.GlobalException;
import com.example.backend.exception.NotFoundException;
import com.example.backend.model.ErrorMessageModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessageModel> notFoundExceptionHandler(NotFoundException ex, WebRequest request){
        ErrorMessageModel errorMessage = new ErrorMessageModel(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity(errorMessage,HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ErrorMessageModel> globalException(GlobalException ex){
        ErrorMessageModel errorMessage = new ErrorMessageModel(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity(errorMessage,HttpStatus.BAD_REQUEST);
    }
}
