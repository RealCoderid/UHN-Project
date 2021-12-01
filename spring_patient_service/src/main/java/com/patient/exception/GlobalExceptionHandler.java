package com.patient.exception;

import java.time.LocalDateTime;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.patient.model.ExceptionResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * This exception is thrown when a specified resource is not present in the
	 * database.
	 * 
	 * @return ExceptionResponse Exception object with error details
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ExceptionResponse> resourceNotFoundException(ResourceNotFoundException ex) {
		logger.info("----- Executing resourceNotFoundException ------");
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorCode("NOT_FOUND");
		response.setErrorMessage(ex.getMessage());
		response.setTimestamp(LocalDateTime.now());
		logger.debug(ex.getMessage(), ex);

		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.NOT_FOUND);
	}

	/**
	 * This exception handler can be customized according to user, customized
	 * message could be passed while calling
	 * 
	 * @return ExceptionResponse Exception object with error details
	 */
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ExceptionResponse> customException(CustomException ex) {
		logger.info("----- Executing customException ------");
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorCode("BAD_REQUEST");
		response.setErrorMessage(ex.getMessage());
		response.setTimestamp(LocalDateTime.now());
		logger.debug(ex.getMessage(), ex);

		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * This exceptions is thrown when the input data to be saved is null or empty
	 * 
	 * @return ExceptionResponse Exception object with error details
	 */
	@ExceptionHandler(TransactionSystemException.class)
	public ResponseEntity<ExceptionResponse> transactionSystemException(TransactionSystemException ex) {
		logger.info("----- Executing transactionSystemException ------");

		String message;
		Throwable cause, resultCause = ex;
		while ((cause = resultCause.getCause()) != null && resultCause != cause) {
			resultCause = cause;
		}
		if (resultCause instanceof ConstraintViolationException) {
			message = (((ConstraintViolationException) resultCause).getConstraintViolations()).iterator().next()
					.getMessage();
		} else {
			resultCause.printStackTrace();
			message = "Invalid input data";
		}

		ExceptionResponse response = new ExceptionResponse();
		response.setErrorCode("BAD_REQUEST");
		response.setErrorMessage(message);
		response.setTimestamp(LocalDateTime.now());
		logger.debug(ex.getMessage(), ex);

		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * This exception handler is used to catch invalid json format requests
	 * 
	 * @return ExceptionResponse Exception object with error details
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ExceptionResponse> httpMessageNotReadableException(HttpMessageNotReadableException ex) {
		logger.info("----- Executing httpMessageNotReadableException ------");
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorCode("BAD_REQUEST");
		response.setErrorMessage(ex.getMessage());
		response.setTimestamp(LocalDateTime.now());
		logger.debug(ex.getMessage(), ex);

		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * This exceptions is caught only when it is not caught by other exception
	 * handlers
	 * 
	 * @return ExceptionResponse Exception object with error details
	 */
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ExceptionResponse> runtimeException(RuntimeException ex) {
		logger.info("----- Executing runtimeException ------");
		ExceptionResponse response = new ExceptionResponse();
		response.setErrorCode("INTERNAL_SERVER_ERROR");
		response.setErrorMessage(ex.getMessage());
		response.setTimestamp(LocalDateTime.now());
		logger.debug(ex.getMessage(), ex);

		return new ResponseEntity<ExceptionResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);

	}

}