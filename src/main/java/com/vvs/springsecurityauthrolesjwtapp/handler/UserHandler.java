package com.vvs.springsecurityauthrolesjwtapp.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.vvs.springsecurityauthrolesjwtapp.dto.UserDto;
import com.vvs.springsecurityauthrolesjwtapp.security.JwtUtil;
import com.vvs.springsecurityauthrolesjwtapp.service.UserService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserHandler {

  private final UserService userService;
  private final JwtUtil jwtUtil;

  public Mono<ServerResponse> usersList(ServerRequest request) {
    return jwtUtil.validateToken(getToken(request))
      .switchIfEmpty(Mono.error(new RuntimeException("Wrong Credentional...")))
      .flatMap(credentionals -> ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(userService.getUsers(), UserDto.class));
  }

  private String getToken(ServerRequest request) {
    return request.headers().firstHeader("authorization").substring(7);
  }
}
