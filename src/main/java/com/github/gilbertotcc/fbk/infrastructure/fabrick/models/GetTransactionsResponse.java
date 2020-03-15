package com.github.gilbertotcc.fbk.infrastructure.fabrick.models;

import lombok.Data;

import java.util.List;

@Data
public class GetTransactionsResponse implements Response<ListPayload<Transaction>> {

  private ResponseStatus status;

  private List<ResponseError> errors;

  private ListPayload<Transaction> payload;
}
