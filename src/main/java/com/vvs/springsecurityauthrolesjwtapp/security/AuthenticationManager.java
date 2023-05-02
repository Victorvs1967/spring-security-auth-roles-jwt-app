package com.vvs.springsecurityauthrolesjwtapp.security;

import java.util.Collections;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import static com.vvs.springsecurityauthrolesjwtapp.security.JwtUtil.KEY_ROLE;

@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

  private final JwtUtil jwtUtil;

  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    String authToken = authentication.getCredentials().toString();

    return Mono.just(authToken)
      .map(token -> jwtUtil.validateToken(token))
      .flatMap(isValid -> jwtUtil.getAllClaimsFToken(authToken))
      .map(claims -> new UsernamePasswordAuthenticationToken(
        claims.getSubject(),
        null,
        Collections.singletonList(new SimpleGrantedAuthority(claims.get(KEY_ROLE).toString()))
      ));
  }
}
