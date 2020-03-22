package com.github.gilbertotcc.fbk.domain.transaction;

import com.github.gilbertotcc.fbk.domain.common.MonetaryAmount;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(example = "GBS_TRANSACTION_TYPE")
    String enumeration;

    @ApiModelProperty(example = "GBS_ACCOUNT_TRANSACTION_TYPE_0050")
    String value;
  }

  @ApiModelProperty(notes = "Id of the transaction.", example = "1520405321")
  private String transactionId;

  @ApiModelProperty(notes = "Id of the operation.", example = "20000048656950")
  private String operationId;

  @ApiModelProperty(notes = "Accounting date.", example = "2020-10-31")
  private LocalDate accountingDate;

  @ApiModelProperty(notes = "Value date.", example = "2020-11-02")
  private LocalDate valueDate;

  @ApiModelProperty(notes = "Type of the transaction")
  private Type type;

  @ApiModelProperty(notes = "Amount of the transaction.")
  private MonetaryAmount amount;

  @ApiModelProperty(notes = "Description of the transaction.", example = "PD BOLLETTINO POSTE")
  private String description;
}
