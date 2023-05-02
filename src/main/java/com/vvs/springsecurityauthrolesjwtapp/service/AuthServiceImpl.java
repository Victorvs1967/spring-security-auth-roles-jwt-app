package com.vvs.springsecurityauthrolesjwtapp.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vvs.springsecurityauthrolesjwtapp.dto.LoginDto;
import com.vvs.springsecurityauthrolesjwtapp.dto.ResponseDto;
import com.vvs.springsecurityauthrolesjwtapp.dto.UserDto;
import com.vvs.springsecurityauthrolesjwtapp.mapper.DataMapper;
import com.vvs.springsecurityauthrolesjwtapp.model.User;
import com.vvs.springsecurityauthrolesjwtapp.repository.UserRepository;
import com.vvs.springsecurityauthrolesjwtapp.security.JwtUtil;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final JwtUtil jwtUtil;
  private final DataMapper mapper;

  @Override
  public Mono<UserDto> signUp(UserDto userDto) {
    return isUserExist(userDto.getUsername())
      .filter(userExist -> !userExist)
      .switchIfEmpty(Mono.error(new RuntimeException("User already exist...")))
      .flatMap(userExist -> isEmailExist(userDto.getEmail())
        .filter(emailExist -> !emailExist)
        .switchIfEmpty(Mono.error(new RuntimeException("Email already exist..."))))
      .map(_boolean -> userDto)
      .map(usrDto -> mapper.convert(usrDto, User.class))
      .doOnNext(user -> user.setPassword(passwordEncoder.encode(user.getPassword())))
      .doOnNext(user -> user.setIsActive(true))
      .flatMap(userRepository::save)
      .map(user -> mapper.convert(user, UserDto.class));
  }

  @Override
  public Mono<ResponseDto> signIn(LoginDto loginDto) {
    return userRepository.findByUsername(loginDto.getUsername())
      .filter(userDetails -> passwordEncoder.matches(loginDto.getPassword(), userDetails.getPassword()))
      .map(userDetails -> jwtUtil.generateToken(userDetails))
      .map(token -> ResponseDto.builder()
        .token(token)
        .build());
  }

  private Mono<Boolean> isUserExist(String username) {
    return userRepository
      .findByUsername(username)
      .map(user -> true)
      .switchIfEmpty(Mono.just(false));
  }

  private Mono<Boolean> isEmailExist(String email) {
    return userRepository
      .findByEmail(email)
      .map(user -> true)
      .switchIfEmpty(Mono.just(false));
  }
}
