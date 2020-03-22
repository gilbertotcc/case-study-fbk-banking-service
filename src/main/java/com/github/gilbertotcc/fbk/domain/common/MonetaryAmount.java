package com.github.gilbertotcc.fbk.domain.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Value;

import java.math.BigDecimal;

@Value(staticConstructor = "with")
public class MonetaryAmount {

  public enum Unit {
    CENTS
  }

  @ApiModelProperty(notes = "Amount expressed in the specified unit.")
  int amount;

  @ApiModelProperty(notes = "Unit used to represent the amount.", example = "CENTS")
  Unit unit;

  @ApiModelProperty(notes = "Currency of the monetary amount.", example = "EUR")
  String currency;

  public static MonetaryAmount toCentMonetaryAmount(BigDecimal bigDecimalAmount, String currency) {
    int amountInCents = bigDecimalAmount.multiply(BigDecimal.valueOf(100)).intValue();
    return MonetaryAmount.with(amountInCents, Unit.CENTS, currency);
  }
}
