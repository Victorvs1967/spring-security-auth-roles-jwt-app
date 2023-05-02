package com.vvs.springsecurityauthrolesjwtapp.service;

import java.time.Instant;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.vvs.springsecurityauthrolesjwtapp.dto.UserDto;
import com.vvs.springsecurityauthrolesjwtapp.mapper.DataMapper;
import com.vvs.springsecurityauthrolesjwtapp.model.User;
import com.vvs.springsecurityauthrolesjwtapp.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final DataMapper mapper;

  @Override
  public Flux<UserDto> getUsers() {
    return userRepository.findAll()
      .map(user -> mapper.convert(user, UserDto.class))
      .switchIfEmpty(Mono.error(new RuntimeException("Users List is Empty...")));
  }

  @Override
  public Mono<UserDto> getUser(String username) {
    return userRepository.findByUsername(username)
      .switchIfEmpty(Mono.error(new RuntimeException("User Not Found...")))
      .map(user -> mapper.convert(user, UserDto.class));
  }

  @Override
  public Mono<UserDto> updateUser(UserDto userDto) {
    return userRepository.findByUsername(userDto.getUsername())
      .switchIfEmpty(Mono.error(new RuntimeException("User Not Found...")))
      .map(user -> User
        .builder()
          .id(user.getId())
          .username(user.getUsername())
          .password(user.getPassword())
          .email(user.getEmail())
          .firstName(userDto.getFirstName())
          .lastName(userDto.getLastName())
          .phone(userDto.getPhone())
          .address(userDto.getAddress())
          .avatar(userDto.getAvatar())
          .onCreate(user.getOnCreate())
          .onUpdate(Date.from(Instant.now()))
          .isActive(userDto.isActive())
          // .isActive(true)
          .role(userDto.getRole())
        .build())
      .flatMap(userRepository::save)
      .map(user -> mapper.convert(user, UserDto.class));
  }

  @Override
  public Mono<UserDto> deleteUser(String username) {
    return userRepository.findByUsername(username)
      .switchIfEmpty(Mono.error(new RuntimeException("User Not Found...")))
      .flatMap(this::delete)
      .map(user -> mapper.convert(user, UserDto.class));
  }

  private Mono<User> delete(User user) {
    return Mono.fromSupplier(() -> {
      userRepository
        .delete(user)
        .subscribe();
      return user;
    });
  }
}
