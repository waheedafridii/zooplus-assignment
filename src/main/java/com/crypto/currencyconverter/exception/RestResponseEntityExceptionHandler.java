package com.crypto.currencyconverter.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    protected ResponseEntity<Object> handleInvalidDataException(
            RuntimeException ex, WebRequest request) {
        String responseBody = "Something went wrong";
        ErrorModel errorModel = ErrorModel.builder()
                .message(responseBody)
                .type("00")
                .build();
        return handleExceptionInternal(ex, errorModel, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = {CryptoException.class})
    @ResponseBody
    protected ResponseEntity<Object> handleCryptoException(
            RuntimeException ex, WebRequest request) {

        CryptoException cryptoException = (CryptoException) ex;

        ErrorModel errorModel = ErrorModel.builder()
                .message(cryptoException.getMessage())
                .type(cryptoException.getClass().getSimpleName())
                .build();
        return handleExceptionInternal(ex, errorModel, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
