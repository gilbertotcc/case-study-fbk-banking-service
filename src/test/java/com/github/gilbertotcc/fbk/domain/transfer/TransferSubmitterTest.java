package com.github.gilbertotcc.fbk.domain.transfer;

import com.github.gilbertotcc.fbk.domain.common.MonetaryAmount;
import com.github.gilbertotcc.fbk.infrastructure.fabrick.FabrickClient;
import com.github.gilbertotcc.fbk.infrastructure.fabrick.models.Transfer;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import static java.time.LocalDate.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TransferSubmitterTest {

  private FabrickClient fabrickClient = mock(FabrickClient.class);

  @Test
  void submitTransferShouldSuccess() {
    String accountId = "accountId";
    when(fabrickClient.createTransferWithAccount(eq(accountId), any()))
      .thenReturn(Try.success(new Transfer()));
    SubmitTransferInput submitTransferInput = someSubmitTransferInput();

    TransferSubmitter transferSubmitter = new TransferSubmitter(accountId, fabrickClient);
    var transferTry = transferSubmitter.submitTransfer(submitTransferInput);

    assertTrue(transferTry.isSuccess());
    verify(fabrickClient).createTransferWithAccount(any(), argThat(argument -> {
      // Some assertions are missing
      assertEquals(submitTransferInput.getDescription(), argument.getDescription());
      return true;
    }));
  }

  private SubmitTransferInput someSubmitTransferInput() {
    MonetaryAmount monetaryAmount = MonetaryAmount.with(100, MonetaryAmount.Unit.CENTS, "EUR");
    return SubmitTransferInput.builder()
      .amount(monetaryAmount)
      .creditorName("Mr. Creditor")
      .description("A test transfer")
      .executionDate(now())
      .iban("IT...")
      .build();
  }
}
