package com.itsspringboot.security;

import com.itsspringboot.exception.AppException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AESPasswordEncoder implements PasswordEncoder {

  private static final Logger logger = LoggerFactory.getLogger(AESPasswordEncoder.class);

  private String aesKey;

  public AESPasswordEncoder(final String aesKey) {
    this.aesKey = aesKey;
  }

  @Override
  public String encode(final CharSequence rawPassword) {
    try {
      return AESCipher.encrypt(rawPassword.toString(), aesKey);
    } catch (Exception e) {
      throw new AppException(e.getMessage(), e);
    }
  }

  @Override
  public boolean matches(final CharSequence rawPassword, final String encodedPassword) {
    try {
      final String decryptedPassword = AESCipher.decrypt(encodedPassword, aesKey);
      return StringUtils.equals(rawPassword, decryptedPassword);

    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return false;
    }
  }
}
