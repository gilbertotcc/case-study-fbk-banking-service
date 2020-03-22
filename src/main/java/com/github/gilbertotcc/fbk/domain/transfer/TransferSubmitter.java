package com.github.gilbertotcc.fbk.domain.transfer;

import com.github.gilbertotcc.fbk.infrastructure.fabrick.FabrickClient;
import com.github.gilbertotcc.fbk.infrastructure.fabrick.models.CreateTransferRequestBody;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
public class TransferSubmitter {

  private final String accountId;

  private final FabrickClient fabrickClient;

  public TransferSubmitter(@Value("${account.id}") String accountId, FabrickClient fabrickClient) {
    this.accountId = accountId;
    this.fabrickClient = fabrickClient;
  }

  public Try<Transfer> submitTransfer(SubmitTransferInput submitTransferInput) {
    log.info("Submit transfer {}", ReflectionToStringBuilder.toString(submitTransferInput, ToStringStyle.JSON_STYLE));
    BigDecimal decimalAmount = BigDecimal.valueOf(submitTransferInput.getAmount().getAmount())
      .divide(BigDecimal.valueOf(100), RoundingMode.HALF_DOWN);
    CreateTransferRequestBody.CreditorAccount creditorAccount =
      CreateTransferRequestBody.CreditorAccount.of(submitTransferInput.getIban());
    CreateTransferRequestBody.Creditor creditor = CreateTransferRequestBody.Creditor.of(
      submitTransferInput.getCreditorName(), creditorAccount
    );
    CreateTransferRequestBody createTransferRequestBody = CreateTransferRequestBody.builder()
      .amount(decimalAmount)
      .creditor(creditor)
      .currency(submitTransferInput.getAmount().getCurrency())
      .description(submitTransferInput.getDescription())
      .executionDate(submitTransferInput.getExecutionDate())
      .build();
    return fabrickClient.createTransferWithAccount(accountId, createTransferRequestBody)
      .map(transfer -> new Transfer());
  }
}
