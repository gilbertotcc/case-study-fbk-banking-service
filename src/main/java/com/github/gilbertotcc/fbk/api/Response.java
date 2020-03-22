package com.github.gilbertotcc.fbk.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Response<T> {

  private final T payload;

  private final String errorMessage;

  public static <T> Response<T> success(T payload) {
    return new Response<>(payload, null);
  }

  public static <T> Response<T> failure(String errorMessage) {
    return new Response<>(null, errorMessage);
  }
}
