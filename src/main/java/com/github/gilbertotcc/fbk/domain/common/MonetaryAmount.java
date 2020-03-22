package com.github.gilbertotcc.fbk.domain.common;

import lombok.Value;

import java.math.BigDecimal;

@Value(staticConstructor = "with")
public class MonetaryAmount {

  public enum Unit {
    CENTS
  }

  int amount;

  Unit unit;

  String currency;

  public static MonetaryAmount toCentMonetaryAmount(BigDecimal bigDecimalAmount, String currency) {
    int amountInCents = bigDecimalAmount.multiply(BigDecimal.valueOf(100)).intValue();
    return MonetaryAmount.with(amountInCents, Unit.CENTS, currency);
  }
}
