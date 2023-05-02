package com.vvs.springsecurityauthrolesjwtapp.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseDto {
  private Object token;
}