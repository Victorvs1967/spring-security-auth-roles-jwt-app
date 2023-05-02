package com.vvs.springsecurityauthrolesjwtapp.security;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

import static com.vvs.springsecurityauthrolesjwtapp.util.ListUtil.toSingleton;

@Component
public class JwtUtil {

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration.in.hours}")
  private long expirationInHours;

  private Key key;

  public static final String KEY_ROLE = "role";

  public String generateToken(UserDetails userDetails) {

    String authority = userDetails
      .getAuthorities().stream()
      .map(GrantedAuthority::getAuthority)
      .collect(toSingleton());

    Map<String, String> claims = new HashMap<>();
    claims.put(KEY_ROLE, authority);

    return Jwts.builder()
      .setClaims(claims)
      .setSubject(userDetails.getUsername())
      .setExpiration(Date.from(Instant.now().plus(Duration.ofHours(expirationInHours))))
      .setIssuedAt(Date.from(Instant.now()))
      .signWith(getKey())
      .compact();
  }

  public Mono<Boolean> validateToken(String token) {
    return getAllClaimsFToken(token)
      .map(Claims::getExpiration)
      .map(expiration -> expiration.after(new Date()));
  }

  public Mono<Claims> getAllClaimsFToken(String token) {
    return Mono.just(
      Jwts
        .parserBuilder()
        .setSigningKey(getKey())
        .build()
        .parseClaimsJws(token)
        .getBody()
      );
  }

  private Key getKey() {
    if (key == null) key = Keys.hmacShaKeyFor(secret.getBytes());
    return key;
  }

}
