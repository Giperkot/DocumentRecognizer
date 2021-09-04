package ru.digitalsoft.document.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.digitalsoft.document.dto.ResultDto;
import ru.digitalsoft.document.dto.exceptions.UserException;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler({ UserException.class})
    public ResponseEntity handleException(UserException ex, WebRequest request) {
        ResultDto resultDto = new ResultDto(false, ex.getMsg());

        return handleExceptionInternal(ex, resultDto,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleValidationExceptions(Exception ex, WebRequest request) {
        ResultDto resultDto = new ResultDto(false, ex.getMessage());

        LOGGER.error("Произошла ошибка", ex);

        return handleExceptionInternal(ex, resultDto,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    public ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                       HttpStatus status, WebRequest request) {

        StringBuilder stringBuilder = new StringBuilder();

        ex.getBindingResult().getAllErrors().forEach((error) -> {

//            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            stringBuilder.append(errorMessage);
            stringBuilder.append("<br/>");
        });

        return handleExceptionInternal(ex, new ResultDto(false, stringBuilder.toString()),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
