package com.vvs.springsecurityauthrolesjwtapp.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final static List<String> ALLOWED_ORIGINS = List.of("*", "http://localhost:4200");
  private final static String[] WHITELIST_AUTH_URL = {"/auth/signup", "/auth/login", "/mail/sendmail"};

  private final AuthenticationManager authenticationManager;
  private final SecurityContextRepository securityContextRepository;

  public CorsConfigurationSource createCorsConfigurationSource() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.setAllowedOrigins(ALLOWED_ORIGINS);
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    source.registerCorsConfiguration("/**", config);
    return source;
  }

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    return http
      .cors()
      .configurationSource(createCorsConfigurationSource())
      .and()
      .exceptionHandling()
      .authenticationEntryPoint((shs, e) -> Mono.fromRunnable(() -> shs.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
      .accessDeniedHandler((shs, e) -> Mono.fromRunnable(() -> shs.getResponse().setStatusCode(HttpStatus.FORBIDDEN)))
      .and()
      .csrf().disable()
      .formLogin().disable()
      .authenticationManager(authenticationManager)
      .securityContextRepository(securityContextRepository)
      .authorizeExchange()
      .pathMatchers(HttpMethod.PUT).hasAnyAuthority("ADMIN", "MODERATOR")
      .pathMatchers(HttpMethod.DELETE).hasAnyAuthority("ADMIN", "MODERATOR")
      .pathMatchers(HttpMethod.OPTIONS).permitAll()
      .pathMatchers(WHITELIST_AUTH_URL).permitAll()
      .anyExchange().authenticated()
      .and()
      .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
