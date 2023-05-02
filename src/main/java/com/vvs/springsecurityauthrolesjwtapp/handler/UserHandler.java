package com.vvs.springsecurityauthrolesjwtapp.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.vvs.springsecurityauthrolesjwtapp.dto.UserDto;
import com.vvs.springsecurityauthrolesjwtapp.security.JwtUtil;
import com.vvs.springsecurityauthrolesjwtapp.service.UserService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

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
        .contentType(APPLICATION_JSON)
        .body(userService.getUsers(), UserDto.class));
  }

  public Mono<ServerResponse> userDetail(ServerRequest request) {
    return jwtUtil.validateToken(getToken(request))
      .switchIfEmpty(Mono.error(new RuntimeException("Wrong Credentionals...")))
      .flatMap(credentionals -> ServerResponse
        .ok()
        .contentType(APPLICATION_JSON)
        .body(userService.getUser(request.pathVariable("username")), UserDto.class));
  }

  public Mono<ServerResponse> userUpdate(ServerRequest request) {
    return jwtUtil.validateToken(getToken(request))
      .switchIfEmpty(Mono.error(new RuntimeException("Wrong Credentionals...")))
      .flatMap(credentionals -> request.bodyToMono(UserDto.class)
        .map(userService::updateUser)
        .flatMap(userDto -> ServerResponse
          .ok()
          .contentType(APPLICATION_JSON)
          .body(userDto, UserDto.class)));
  }

  public Mono<ServerResponse> userDelete(ServerRequest request) {
    return jwtUtil.validateToken(getToken(request))
      .switchIfEmpty(Mono.error(new RuntimeException("Wrong Credentionals...")))
      .flatMap(credentionals -> ServerResponse
        .ok()
        .contentType(APPLICATION_JSON)
        .body(userService.deleteUser(request.pathVariable("username")), UserDto.class));
  }

  private String getToken(ServerRequest request) {
    return request.headers().firstHeader("authorization").substring(7);
  }
}
