package com.vvs.springsecurityauthrolesjwtapp.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.vvs.springsecurityauthrolesjwtapp.handler.UserHandler;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;

@Configuration
public class UserRouter {

  @Bean
  public RouterFunction<ServerResponse> userRouterFunction(UserHandler handler) {
    return route()
      .nest(path("/api/users"), builder -> builder
        .GET("", handler::usersList))
      .build();
  }

}
