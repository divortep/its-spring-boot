package com.itsspringboot.security;

import com.itsspringboot.model.User;
import com.itsspringboot.model.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

  private static final String TOKEN_TYPE = "JWT";
  private static final String TOKEN_ISSUER = "its-api";
  private static final String TOKEN_AUDIENCE = "its-app";
  private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.expirationInMs}")
  private long jwtExpirationInMs;

  public String generateToken(final Authentication authentication) {
    final UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
    final User user = userPrincipal.getUser();
    final Date now = new Date();
    final Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);


    return Jwts.builder()
        .setSubject(userPrincipal.getId())
        .setIssuer(TOKEN_ISSUER)
        .setIssuedAt(new Date())
        .setAudience(TOKEN_AUDIENCE)
        .setHeaderParam("typ", TOKEN_TYPE)
        .setExpiration(expiryDate)
        .claim("id", user.getId())
        .claim("username", user.getUsername())
        .claim("name", user.getName())
        .signWith(getSigningKey(), SignatureAlgorithm.HS512)
        .compact();
  }

  public String getUserIdFromJWT(final String token) {
    final Claims claims = Jwts.parser()
        .setSigningKey(getSigningKey())
        .parseClaimsJws(token)
        .getBody();

    return claims.getSubject();
  }

  public boolean validateToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException ex) {
      logger.error("Invalid JWT signature.");
    } catch (MalformedJwtException ex) {
      logger.error("Invalid JWT token.");
    } catch (ExpiredJwtException ex) {
      logger.error("Expired JWT token.");
    } catch (UnsupportedJwtException ex) {
      logger.error("Unsupported JWT token.");
    } catch (IllegalArgumentException ex) {
      logger.error("JWT claims string is empty.");
    }
    return false;
  }

  private Key getSigningKey() {
    return Keys.hmacShaKeyFor(jwtSecret.getBytes());
  }
}
