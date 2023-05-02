package com.vvs.springsecurityauthrolesjwtapp.service;

import java.util.Properties;

import org.springframework.stereotype.Component;

import com.vvs.springsecurityauthrolesjwtapp.dto.MailResponseDto;

import jakarta.mail.Authenticator;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.Message.RecipientType;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

  private final MailConfig config;

  @Override
  public Mono<Void> sendMail(MailResponseDto mail) {
    Properties properties = new Properties();
    properties.put("mail.transport.protocol", "smtp");
    properties.put("mail.smtp.host", config.getHost());
    properties.put("mail.smtp.port", config.getPort());
    properties.put("mail.smtp.ssl.enable", config.getSsl());
    properties.put("mail.smtp.starttls.enable", config.getTls());
    properties.put("mail.smtp.auth", config.getAuth());

    Session session = Session.getInstance(properties, new Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(config.getUsername(), config.getPassword());
      }
    });
    session.setDebug(config.getDebug());

    try {
      MimeMessage message = new MimeMessage(session);

      message.setFrom(new InternetAddress(config.getUsername()));
      message.setRecipient(RecipientType.TO, new InternetAddress(mail.getMailTo()));
      message.setSubject(mail.getSubject());
      message.setContent(mail.getMessage(), "text/html");

      Transport.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
    return Mono.empty();
  }
}
