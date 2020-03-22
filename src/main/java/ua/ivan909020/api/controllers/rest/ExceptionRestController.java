package ua.ivan909020.api.controllers.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ua.ivan909020.api.domain.dto.ErrorResponseDto;
import ua.ivan909020.api.exceptions.EntityNotFoundException;
import ua.ivan909020.api.exceptions.ValidationException;

@RestControllerAdvice
public class ExceptionRestController extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		FieldError error = ex.getBindingResult().getFieldError();
		String message = error != null ? error.getDefaultMessage() : ex.getMessage();
		return ResponseEntity.status(status).body(buildResponse(status, message));
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return ResponseEntity.status(status).body(buildResponse(status, ex.getMessage()));
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public ErrorResponseDto handleEntityNotFound(EntityNotFoundException ex) {
		return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
	}

	@ExceptionHandler(ValidationException.class)
	public ErrorResponseDto handleValidation(ValidationException ex) {
		return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public ErrorResponseDto handleAllExceptions(Exception ex) {
		return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
	}

	private ErrorResponseDto buildResponse(HttpStatus status, String message) {
		return new ErrorResponseDto(status.value(), status.getReasonPhrase(), message);
	}

}
