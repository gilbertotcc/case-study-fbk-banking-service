package com.github.gilbertotcc.fbk.infrastructure.fabrick.models;

import java.util.List;

public interface Response<T> {

  ResponseStatus getStatus();

  List<ResponseError> getErrors();

  T getPayload();
}
