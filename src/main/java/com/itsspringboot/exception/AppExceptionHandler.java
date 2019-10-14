package com.itsspringboot.exception;

import com.itsspringboot.service.EmailNotificationService;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

  private EmailNotificationService emailNotificationService;

  @Autowired
  public AppExceptionHandler(final EmailNotificationService emailNotificationService) {
    this.emailNotificationService = emailNotificationService;
  }

  @ExceptionHandler(AppException.class)
  protected void handleException(RuntimeException ex, WebRequest request) {
    final StringWriter stacktrace = new StringWriter();
    ex.printStackTrace(new PrintWriter(stacktrace));
    emailNotificationService.notifyAdmin("An error in ITS app", stacktrace.toString());
  }
}
