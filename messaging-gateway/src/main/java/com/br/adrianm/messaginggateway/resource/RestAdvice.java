package com.br.adrianm.messaginggateway.resource;

import com.br.adrianm.messaginggateway.config.exceptions.ConnectionException;
import com.br.adrianm.messaginggateway.config.exceptions.SendMessageException;
import com.br.adrianm.messaginggateway.resource.dto.ApiErrorsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class RestAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConnectionException.class)
    public ApiErrorsDTO handleConnectionErrors(ConnectionException ex) {
        return new ApiErrorsDTO(List.of(ex.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SendMessageException.class)
    public ApiErrorsDTO handleMessageErrors(SendMessageException ex) {
        return new ApiErrorsDTO(List.of(ex.getMessage()));
    }
}
