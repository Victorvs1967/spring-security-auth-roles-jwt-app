package com.vvs.springsecurityauthrolesjwtapp.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.vvs.springsecurityauthrolesjwtapp.dto.LoginDto;
import com.vvs.springsecurityauthrolesjwtapp.dto.ResponseDto;
import com.vvs.springsecurityauthrolesjwtapp.dto.UserDto;
import com.vvs.springsecurityauthrolesjwtapp.service.AuthService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthHandler {

  private final AuthService auth;

  public Mono<ServerResponse> signUp(ServerRequest request) {
    return request.bodyToMono(UserDto.class)
      .flatMap(userDto -> auth.signUp(userDto))
      .flatMap(userDto -> ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(userDto, UserDto.class)
        .switchIfEmpty(Mono.error(new RuntimeException("Sign Up Falture...")))
      );
  }

  public Mono<ServerResponse> login(ServerRequest request) {
    return request.bodyToMono(LoginDto.class)
      .flatMap(credentials -> auth.signIn(credentials))
      .flatMap(response -> ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(response, ResponseDto.class)
        .switchIfEmpty(Mono.error(new RuntimeException("Login Falture...")))
      );
  }
}
