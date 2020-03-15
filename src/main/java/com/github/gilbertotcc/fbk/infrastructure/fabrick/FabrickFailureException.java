package com.github.gilbertotcc.fbk.infrastructure.fabrick;

import com.github.gilbertotcc.fbk.infrastructure.fabrick.models.Response;
import com.github.gilbertotcc.fbk.infrastructure.fabrick.models.ResponseError;

import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Getter
public class FabrickFailureException extends RuntimeException {

  private static final long serialVersionUID = -6607108771293326111L;

  private final transient List<ResponseError> errors;

  public FabrickFailureException(ResponseEntity<? extends Response<?>> response) {
    super("Fabrick endpoint call failed");
    this.errors = Optional.of(response)
      .map(ResponseEntity::getBody)
      .map(Response::getErrors)
      .orElse(emptyList());
  }
}
