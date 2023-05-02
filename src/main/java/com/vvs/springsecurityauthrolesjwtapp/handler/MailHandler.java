package com.vvs.springsecurityauthrolesjwtapp.handler;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.vvs.springsecurityauthrolesjwtapp.dto.MailResponseDto;
import com.vvs.springsecurityauthrolesjwtapp.service.MailService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MailHandler {

  private final MailService mailService;

  public Mono<ServerResponse> sendMail(ServerRequest request) {
    return request.bodyToMono(MailResponseDto.class)
      .switchIfEmpty(Mono.error(new RuntimeException("Wrong sent message...")))
      .map(mailService::sendMail)
      .flatMap(_res -> ServerResponse
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(Mono.just(Map.of("message", "Message sent successfully")), Map.class));
  }
}
