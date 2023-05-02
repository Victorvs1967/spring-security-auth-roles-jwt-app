package com.vvs.springsecurityauthrolesjwtapp.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Getter
@Configuration
public class MailConfig {

  @Value("${mail.smtp.port}")
  private int port;

  @Value("${mail.smtp.host}")
  private String host;

  @Value("${mail.smtp.auth}")
  private Boolean auth;

  @Value("${mail.smtp.starttls.enable}")
  private Boolean tls;

  @Value("${mail.smtp.ssl.enable}")
  private Boolean ssl;

  @Value("${mail.smtp.debug}")
  private Boolean debug;

  @Value("${mail.smtp.username}")
  private String username;

  @Value("${mail.smtp.password}")
  private String password;

}
