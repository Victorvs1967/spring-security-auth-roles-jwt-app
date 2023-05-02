package com.vvs.springsecurityauthrolesjwtapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MailResponseDto {

  @NotNull
  private String subject;

  @NotNull
  @Email
  private String mailTo;

  @NotNull
  @Min(10)
  private String message;
}
