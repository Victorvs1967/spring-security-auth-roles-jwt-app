package com.vvs.springsecurityauthrolesjwtapp.util;

import java.nio.channels.IllegalSelectorException;
import java.util.stream.Collector;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

public class ListUtil {

  public static <T> Collector<T, ?, T> toSingleton() {
    return collectingAndThen(toList(), list -> {
      if (list.size() != 1) throw new IllegalSelectorException();
      return list.get(0);
    });
  }
}
