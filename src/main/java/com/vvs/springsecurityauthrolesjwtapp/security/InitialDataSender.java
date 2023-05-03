package com.vvs.springsecurityauthrolesjwtapp.security;

import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.vvs.springsecurityauthrolesjwtapp.model.Role;
import com.vvs.springsecurityauthrolesjwtapp.model.User;
import com.vvs.springsecurityauthrolesjwtapp.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitialDataSender implements ApplicationListener<ApplicationStartedEvent> {

  @Value("${admin.username}")
  private String username;
  @Value("${admin.password}")
  private String password;
  @Value("${admin.email}")
  private String email;

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void onApplicationEvent(ApplicationStartedEvent event) {
    userRepository
      .findByUsername(username)
      .switchIfEmpty(createAdmin())
      .subscribe();
  }

  private Mono<User> createAdmin() {
    User user = User
      .builder()
        .username(username)
        .password(passwordEncoder.encode(password))
        .email(email)
        .role(Role.ADMIN)
        .onCreate(Date.from(Instant.now()))
        .onUpdate(Date.from(Instant.now()))
        .isActive(true)
      .build();

    return userRepository
      .save(user)
      .doOnNext(admin -> log.info("Admin user created successfully..."));
  }
}
