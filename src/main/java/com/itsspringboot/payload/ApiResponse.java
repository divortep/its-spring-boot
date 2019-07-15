package com.itsspringboot.payload;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode
@ToString
public class ApiResponse {

  private Boolean success;
  private String message;

  public ApiResponse(final Boolean success, final String message) {
    this.success = success;
    this.message = message;
  }
}
