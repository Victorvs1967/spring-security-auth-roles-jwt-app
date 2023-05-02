package com.vvs.springsecurityauthrolesjwtapp.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.vvs.springsecurityauthrolesjwtapp.handler.MailHandler;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;;

@Configuration
public class MailRouter {

  @Bean
  public RouterFunction<ServerResponse> mailRouterFunction(MailHandler handler) {
    return route()
      .nest(path("/mail"), builder -> builder
        .POST("/sendmail", handler::sendMail))
      .build();
  }
}
