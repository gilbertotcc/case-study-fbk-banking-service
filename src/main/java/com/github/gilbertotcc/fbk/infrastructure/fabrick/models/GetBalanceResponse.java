package com.github.gilbertotcc.fbk.infrastructure.fabrick.models;

import lombok.Data;

import java.util.List;

@Data
public class GetBalanceResponse implements Response<Balance> {

  private ResponseStatus status;

  private List<ResponseError> errors;

  private Balance payload;
}
