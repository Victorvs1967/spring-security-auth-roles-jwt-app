package com.vvs.springsecurityauthrolesjwtapp.service;

import com.vvs.springsecurityauthrolesjwtapp.dto.MailResponseDto;

import reactor.core.publisher.Mono;

public interface MailService {
  Mono<Void> sendMail(MailResponseDto mail);
}
