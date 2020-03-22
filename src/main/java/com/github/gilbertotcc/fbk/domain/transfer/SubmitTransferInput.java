package com.github.gilbertotcc.fbk.domain.transfer;

import com.github.gilbertotcc.fbk.domain.common.MonetaryAmount;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class SubmitTransferInput {

  private final String creditorName;

  private final String iban;

  private final String description;

  private final MonetaryAmount amount;

  private final LocalDate executionDate;
}
