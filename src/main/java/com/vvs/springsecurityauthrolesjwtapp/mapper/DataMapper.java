package com.vvs.springsecurityauthrolesjwtapp.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toList;

@Component
public class DataMapper {

  private ModelMapper mapper = new ModelMapper();

  public <T, R> R convert(T item, Class<R> typeParameterClass) {
    return mapper.map(item, typeParameterClass);
  }

  public <T, R> List<R> convertToList(List<T> list, Class<R> typeParameterClass) {
    return list.stream()
      .map(item -> mapper.map(item, typeParameterClass))
      .collect(toList());
  }
}
