package com.itsspringboot.payload;

import com.itsspringboot.model.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode
@ToString
public class JwtAuthResponse {

  private String accessToken;
  private String tokenType = "Bearer";

  public JwtAuthResponse(final String accessToken) {
    this.accessToken = accessToken;
  }
}
