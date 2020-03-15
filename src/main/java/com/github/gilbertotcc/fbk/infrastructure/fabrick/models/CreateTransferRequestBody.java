package com.github.gilbertotcc.fbk.infrastructure.fabrick.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class CreateTransferRequestBody {

  @Value(staticConstructor = "of")
  public static class Creditor {

    String name;

    CreditorAccount account;
  }

  @Value(staticConstructor = "of")
  public static class CreditorAccount {

    String accountCode;
  }

  private Creditor creditor;

  private String description;

  private String currency;

  @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
  private BigDecimal amount;

  private LocalDate executionDate;

  // Optional fields are ignored
}
