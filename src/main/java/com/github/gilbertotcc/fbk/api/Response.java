package com.github.gilbertotcc.fbk.api;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Response<T> {

  @ApiModelProperty(notes = "Result of the operation.")
  private final T payload;

  @ApiModelProperty(notes = "Error description, if operation fails with an error.")
  private final String errorMessage;

  public static <T> Response<T> success(T payload) {
    return new Response<>(payload, null);
  }

  public static <T> Response<T> failure(String errorMessage) {
    return new Response<>(null, errorMessage);
  }
}
