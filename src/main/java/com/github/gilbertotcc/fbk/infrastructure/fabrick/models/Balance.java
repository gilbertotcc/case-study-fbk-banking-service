package com.github.gilbertotcc.fbk.infrastructure.fabrick.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Balance {

  private LocalDate date;

  @JsonProperty("balance")
  @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
  private BigDecimal balanceAmount;

  @JsonProperty("availableBalance")
  @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
  private BigDecimal availableBalanceAmount;

  private String currency;
}
