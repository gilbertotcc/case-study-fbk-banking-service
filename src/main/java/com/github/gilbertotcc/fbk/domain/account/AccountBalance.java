package com.github.gilbertotcc.fbk.domain.account;

import com.github.gilbertotcc.fbk.domain.common.MonetaryAmount;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AccountBalance {

  @ApiModelProperty(notes = "Account's balance.")
  MonetaryAmount balance;

  @ApiModelProperty(notes = "Account's available balance.")
  MonetaryAmount availableBalance;

  @ApiModelProperty(notes = "Date the balances refer to.")
  LocalDate date;
}
