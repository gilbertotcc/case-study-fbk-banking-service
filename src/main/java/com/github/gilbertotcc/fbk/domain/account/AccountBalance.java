package com.github.gilbertotcc.fbk.domain.account;

import com.github.gilbertotcc.fbk.domain.common.MonetaryAmount;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AccountBalance {

  MonetaryAmount balance;

  MonetaryAmount availableBalance;

  LocalDate date;
}
