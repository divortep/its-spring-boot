package com.itsspringboot.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

  private String stackTraceMessage;

  public BadRequestException(String message) {
    super(message);
  }

  public BadRequestException(String message, Throwable cause) {
    super(message, cause);
    this.stackTraceMessage = ExceptionUtils.getStackTrace(cause);
  }

  public String getStackTraceMessage() {
    return stackTraceMessage;
  }

  public void setStackTraceMessage(final String stackTraceMessage) {
    this.stackTraceMessage = stackTraceMessage;
  }

}
