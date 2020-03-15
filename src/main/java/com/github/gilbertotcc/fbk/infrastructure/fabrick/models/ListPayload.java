package com.github.gilbertotcc.fbk.infrastructure.fabrick.models;

import lombok.Data;

import java.util.List;

@Data
public class ListPayload<T> {

  private List<T> list;
}
