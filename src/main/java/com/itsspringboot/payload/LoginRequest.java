package com.itsspringboot.payload;

import javax.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode
@ToString
public class LoginRequest {

  @NotBlank
  private String email;

  @NotBlank
  private String password;
}
