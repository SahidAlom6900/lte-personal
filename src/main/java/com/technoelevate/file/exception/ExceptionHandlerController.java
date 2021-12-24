package com.technoelevate.file.exception;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.validation.ConstraintViolationException;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ CustomException.class, IOException.class, ServletException.class ,FileNotFoundException.class})
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorMessage IdNotFound(CustomException exception, WebRequest request) {
		return new ErrorMessage(HttpStatus.BAD_REQUEST.value(), new Date(), "BAD REQUEST", exception.getMessage(),
				request.getDescription(false));
	}

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ErrorMessage badRequest(CustomException exception, WebRequest request) {
		return new ErrorMessage(HttpStatus.NOT_FOUND.value(), new Date(), "NOT FOUND", "Customer Not Found!!!",
				request.getDescription(false));
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		List<String> errors = exception.getBindingResult().getFieldErrors().stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
		body.put("error", true);
		body.put("message", errors);
		body.put("path", request.getDescription(false));
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<?> constraintViolationException(ConstraintViolationException exception, WebRequest request) {
		List<String> errors = new ArrayList<>();
		exception.getConstraintViolations().forEach(cv -> errors.add(cv.getMessage()));
		Map<String, Object> result = new LinkedHashMap<>();
		result.put("error", true);
		result.put("message", errors);
		result.put("path", request.getDescription(false));
		return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage internalServerError(Exception exception, WebRequest request) {
		return new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), "INTERNAL SERVER ERROR",
				exception.getMessage(), request.getDescription(false));
	}
}
