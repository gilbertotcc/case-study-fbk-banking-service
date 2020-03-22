package com.github.gilbertotcc.fbk.infrastructure.fabrick.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Transaction {

  @Data
  public static class Type {

    String enumeration;

    String value;
  }

  private String transactionId;

  private String operationId;

  private LocalDate accountingDate;

  private LocalDate valueDate;

  private Type type;

  @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
  private BigDecimal amount;

  private String currency;

  private String description;
}
