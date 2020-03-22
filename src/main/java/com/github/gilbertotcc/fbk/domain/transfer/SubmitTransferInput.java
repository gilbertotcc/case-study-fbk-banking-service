package com.github.gilbertotcc.fbk.domain.transfer;

import com.github.gilbertotcc.fbk.domain.common.MonetaryAmount;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class SubmitTransferInput {

  @ApiModelProperty(notes = "Name of the creditor.")
  private final String creditorName;

  @ApiModelProperty(notes = "Creditor's account IBAN.")
  private final String iban;

  @ApiModelProperty(notes = "Description of the transaction.", example = "PD BOLLETTINO POSTE")
  private final String description;

  @ApiModelProperty(notes = "Amount of the transfer.")
  private final MonetaryAmount amount;

  @ApiModelProperty(notes = "Execution of the transfer.")
  private final LocalDate executionDate;
}
