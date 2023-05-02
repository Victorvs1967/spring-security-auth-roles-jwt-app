package com.vvs.springsecurityauthrolesjwtapp.service;

import com.vvs.springsecurityauthrolesjwtapp.dto.LoginDto;
import com.vvs.springsecurityauthrolesjwtapp.dto.ResponseDto;
import com.vvs.springsecurityauthrolesjwtapp.dto.UserDto;

import reactor.core.publisher.Mono;

public interface AuthService {

  public Mono<UserDto> signUp(UserDto userDto);
  public Mono<ResponseDto> signIn(LoginDto loginDto);
}
