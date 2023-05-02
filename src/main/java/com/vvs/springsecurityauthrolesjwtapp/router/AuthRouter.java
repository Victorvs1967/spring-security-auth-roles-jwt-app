package com.vvs.springsecurityauthrolesjwtapp.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.vvs.springsecurityauthrolesjwtapp.handler.AuthHandler;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;

@Configuration
public class AuthRouter {

  @Bean
  public RouterFunction<ServerResponse>  authRouterFunction(final AuthHandler handler) {
    return route()
      .nest(path("/auth"), builder -> builder
        .POST("/signup", handler::signUp)
        .POST("/login", handler::login))
      .build();
  }
}
