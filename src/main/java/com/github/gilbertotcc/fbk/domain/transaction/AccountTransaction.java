package com.github.gilbertotcc.fbk.domain.transaction;

import com.github.gilbertotcc.fbk.domain.common.MonetaryAmount;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.time.LocalDate;

@Builder
@Getter
public class AccountTransaction {

  @Value(staticConstructor = "with")
  @Getter
  public static class Type {

    String enumeration;

    String value;
  }

  private String transactionId;

  private String operationId;

  private LocalDate accountingDate;

  private LocalDate valueDate;

  private Type type;

  private MonetaryAmount amount;

  private String description;
}
