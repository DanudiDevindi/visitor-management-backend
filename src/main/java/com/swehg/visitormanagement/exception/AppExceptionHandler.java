package com.swehg.visitormanagement.exception;

import com.swehg.visitormanagement.dto.response.ErrorMessageResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.swehg.visitormanagement.constant.ApplicationConstant.APPLICATION_ERROR_OCCURRED_MESSAGE;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {Exception.class})
    ResponseEntity<ErrorMessageResponseDTO> handleAnyException(Exception ex, WebRequest webRequest) {
        return new ResponseEntity<>(
                new ErrorMessageResponseDTO(false, 100, APPLICATION_ERROR_OCCURRED_MESSAGE), HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(value = {UserException.class})
    ResponseEntity<ErrorMessageResponseDTO> handleAdminException(UserException ex, WebRequest webRequest) {
        return new ResponseEntity<>(
                new ErrorMessageResponseDTO(false, ex.getStatus(), ex.getMsg()), HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(value = {VisitException.class})
    ResponseEntity<ErrorMessageResponseDTO> handleVisitException(VisitException ex, WebRequest webRequest) {
        return new ResponseEntity<>(
                new ErrorMessageResponseDTO(false, ex.getStatus(), ex.getMsg()), HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(value = {VisitorException.class})
    ResponseEntity<ErrorMessageResponseDTO> handleVisitorException(VisitorException ex, WebRequest webRequest) {
        return new ResponseEntity<>(
                new ErrorMessageResponseDTO(false, ex.getStatus(), ex.getMsg()), HttpStatus.EXPECTATION_FAILED);
    }

}
