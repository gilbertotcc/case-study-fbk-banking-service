package com.github.gilbertotcc.fbk.infrastructure.fabrick.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CreateTransferResponse implements Response<Transfer> {

  private ResponseStatus status;

  @JsonProperty("error")
  private List<ResponseError> errors;

  private Transfer payload;
}
