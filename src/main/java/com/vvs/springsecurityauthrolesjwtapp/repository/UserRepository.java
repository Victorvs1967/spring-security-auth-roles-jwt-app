package com.vvs.springsecurityauthrolesjwtapp.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.vvs.springsecurityauthrolesjwtapp.model.User;

import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String> {
  Mono<UserDetails> findByUsername(String username);
  Mono<UserDetails> findByEmail(String email);
}
