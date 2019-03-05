package com.store.exception;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.store.model.Response;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomizedResponseEntityExceptionHandler.class);
	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Response> handleAllExceptions(Exception ex, WebRequest request) {
		LOGGER.error("Unable to process the request", ex);
		Response exceptionResponse = new Response(new Date(), ex.getMessage(), request.getDescription(false),
				HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(BookNotFoundException.class)
	public final ResponseEntity<Response> handleBookNotFoundException(BookNotFoundException ex, WebRequest request) {
		LOGGER.error("Unable to find book ", ex.getMessage());
		Response exceptionResponse = new Response(new Date(), ex.getMessage(), request.getDescription(false),
				HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		LOGGER.error("Missing required parameter ", ex.getMessage());
		Response exceptionResponse = new Response(new Date(),
				messageSource.getMessage("book.validation", null, LocaleContextHolder.getLocale()),
				ex.getBindingResult().getFieldErrors().toString(), HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		LOGGER.error("Missing book name in request ", ex.getMessage());
		Response exceptionResponse = new Response(new Date(),
				messageSource.getMessage("book.validation", null, LocaleContextHolder.getLocale()),
				ex.getParameterName() + " "
						+ messageSource.getMessage("book.missing.parameter", null, LocaleContextHolder.getLocale()),
				HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BookUniqueConstraintViolationException.class)
	public final ResponseEntity<Response> handleBookUniqueConstraintException(BookUniqueConstraintViolationException ex,
			WebRequest request) {
		LOGGER.error("Unable to save book detail ", ex.getMessage());
		Response exceptionResponse = new Response(new Date(), ex.getMessage(), request.getDescription(false),
				HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}