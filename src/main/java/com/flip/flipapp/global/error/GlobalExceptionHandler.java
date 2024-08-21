package com.flip.flipapp.global.error;

import com.flip.flipapp.domain.account.controller.dto.response.AccountSuspendedResponse;
import com.flip.flipapp.domain.account.exception.AccountSuspendedException;
import com.flip.flipapp.global.error.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private static final String DEFAULT_LOG_MESSAGE = "Exception: {}, Message: {}";

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    log.error(DEFAULT_LOG_MESSAGE, ex.getClass(), ex.getMessage());
    ErrorResponse errorResponse = ErrorResponse.of(CommonErrorCode.INVALID_INPUT_VALUE,
        ex.getBindingResult());
    return ResponseEntity.badRequest().headers(headers).body(errorResponse);
  }

  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
      HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status,
      WebRequest request) {
    log.error(DEFAULT_LOG_MESSAGE, ex.getClass(), ex.getMessage());
    return ResponseEntity.badRequest().body(ErrorResponse.of(CommonErrorCode.INVALID_REQUEST));
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
      HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
    log.error(DEFAULT_LOG_MESSAGE, ex.getClass(), ex.getMessage());

    return ResponseEntity.internalServerError()
        .body(ErrorResponse.of(CommonErrorCode.INTERNAL_SERVER_ERROR));
  }

  /**
   * javax.validation.ConstraintViolationException 으로 제약조건을 위반할 때 발생한다. 주로 @Min, @Max 등 제약을 두는
   * 어노테이션에서 발생
   */
  @ExceptionHandler(ConstraintViolationException.class)
  protected ResponseEntity<ErrorResponse> handleConstraintViolationException(
      ConstraintViolationException e, HttpServletRequest request) {
    log.error(DEFAULT_LOG_MESSAGE, e.getClass(), e.getMessage());
    return ResponseEntity.badRequest().body(ErrorResponse.of(CommonErrorCode.INVALID_REQUEST));
  }

  /**
   * BusinessException 을 처리, 비즈니스 로직상에서 BusinessException을 상속한 예외 발생시 처리된다
   *
   * @param e
   * @return ResponseEntity<ErrorResponse>
   */
  @ExceptionHandler(BusinessException.class)
  protected ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e,
      HttpServletRequest request) {
    log.error(DEFAULT_LOG_MESSAGE, e.getClass(), e.getMessage());
    return ResponseEntity.status(e.getErrorCode().getStatus())
        .body(ErrorResponse.of(e.getErrorCode()));
  }

  /**
   * 계정이 정지된 경우 발생하는 AccountSuspendedException을 처리합니다. 계정 정지에 대한 세부 정보를 포함한 403 Forbidden 응답을
   * 반환합니다.
   *
   * @param e 서비스 레이어에서 발생한 AccountSuspendedException
   * @return 계정 정지에 대한 세부 정보를 담은 AccountSuspendedResponse를 포함하는 ResponseEntity
   */
  @ExceptionHandler(AccountSuspendedException.class)
  public ResponseEntity<AccountSuspendedResponse> handleAccountSuspendedException(
      AccountSuspendedException e) {
    AccountSuspendedResponse response = new AccountSuspendedResponse(
        e.getSuspendedAt(), e.getAccountState(), e.getBlameTypes()
    );
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
  }
}
