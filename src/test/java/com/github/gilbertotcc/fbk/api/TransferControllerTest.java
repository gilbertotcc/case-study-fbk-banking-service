package com.github.gilbertotcc.fbk.api;

import com.github.gilbertotcc.fbk.domain.common.MonetaryAmount;
import com.github.gilbertotcc.fbk.domain.transfer.SubmitTransferInput;
import com.github.gilbertotcc.fbk.domain.transfer.Transfer;
import com.github.gilbertotcc.fbk.domain.transfer.TransferSubmitter;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import static java.time.LocalDate.now;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TransferControllerTest {

  private final TransferSubmitter transferSubmitter = mock(TransferSubmitter.class);

  @Test
  void submitTransferShouldSuccess() {
    SubmitTransferInput submitTransferInput = someTransfer();
    when(transferSubmitter.submitTransfer(any(SubmitTransferInput.class)))
      .thenReturn(Try.success(new Transfer()));

    TransferController controller = new TransferController(transferSubmitter);

    var responseEntity = controller.submitTransfer(submitTransferInput);

    verify(transferSubmitter).submitTransfer(eq(submitTransferInput));
  }

  private SubmitTransferInput someTransfer() {
    return SubmitTransferInput.builder()
      .iban("anIban")
      .executionDate(now())
      .description("Transfer description")
      .creditorName("aCreditor")
      .amount(MonetaryAmount.with(100, MonetaryAmount.Unit.CENTS, "EUR"))
      .build();
  }
}
