package com.vvs.springsecurityauthrolesjwtapp.service;

import com.vvs.springsecurityauthrolesjwtapp.dto.UserDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {

  public Flux<UserDto> getUsers();
  public Mono<UserDto> getUser(String username);
  public Mono<UserDto> updateUser(UserDto userDto);
  public Mono<UserDto> deleteUser(String username);
}
