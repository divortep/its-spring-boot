package com.itsspringboot.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AppException extends RuntimeException {

  private String stackTraceMessage;

  public AppException(String message, Throwable cause) {
    super(message, cause);
    this.stackTraceMessage = ExceptionUtils.getStackTrace(cause);
  }

  public AppException(String message) {
    super(message);
  }


  public String getStackTraceMessage() {
    return stackTraceMessage;
  }

  public void setStackTraceMessage(final String stackTraceMessage) {
    this.stackTraceMessage = stackTraceMessage;
  }

}
