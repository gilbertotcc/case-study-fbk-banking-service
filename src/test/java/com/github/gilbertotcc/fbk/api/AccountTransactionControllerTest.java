package com.github.gilbertotcc.fbk.api;

import com.github.gilbertotcc.fbk.domain.transaction.AccountTransaction;
import com.github.gilbertotcc.fbk.domain.transaction.AccountTransactionRetriever;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.time.LocalDate.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountTransactionControllerTest {

  private final AccountTransactionRetriever accountTransactionRetriever = mock(AccountTransactionRetriever.class);

  @Test
  void retrieveAccountTransactionShouldSuccess() {
    AccountTransaction accountTransaction = someAccountTransaction();
    when(accountTransactionRetriever.getTransactions(now(), now()))
      .thenReturn(Try.success(List.of(accountTransaction)));

    AccountTransactionController accountTransactionController =
      new AccountTransactionController(accountTransactionRetriever);

    var responseEntity = accountTransactionController.retrieveAccountTransactions(now(), now());

    var retrievedTransaction = responseEntity.getBody().getPayload().get(0);
    assertEquals(accountTransaction, retrievedTransaction);
  }

  private AccountTransaction someAccountTransaction() {
    return AccountTransaction.builder()
      .build();
  }
}
