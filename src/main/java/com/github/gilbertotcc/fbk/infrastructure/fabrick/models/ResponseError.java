package com.github.gilbertotcc.fbk.infrastructure.fabrick.models;

import lombok.Value;

@Value
public class ResponseError {

  String code;

  String description;

  String params;
}
