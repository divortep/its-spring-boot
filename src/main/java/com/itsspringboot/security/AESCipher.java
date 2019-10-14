package com.itsspringboot.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Decoder;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESCipher {

  public static final String AES_CIPHER = "AES/ECB/PKCS5Padding";

  public static String encrypt(final String text, final String aesKey) throws NoSuchPaddingException, NoSuchAlgorithmException,
      InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

    final Cipher cipher = Cipher.getInstance(AES_CIPHER);
    final SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(aesKey), "AES");
    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    return Base64.getEncoder().encodeToString(cipher.doFinal(text.getBytes()));
  }

  public static String decrypt(final String text, final String aesKey) throws NoSuchPaddingException, NoSuchAlgorithmException,
      InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

    final Cipher cipher = Cipher.getInstance(AES_CIPHER);
    final Decoder base64Decoder = Base64.getDecoder();
    final SecretKey secretKey = new SecretKeySpec(base64Decoder.decode(aesKey), "AES");
    cipher.init(Cipher.DECRYPT_MODE, secretKey);
    return new String(cipher.doFinal(Base64.getDecoder().decode(text)));
  }
}
