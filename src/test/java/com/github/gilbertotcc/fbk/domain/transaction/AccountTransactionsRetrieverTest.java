package com.github.gilbertotcc.fbk.domain.transaction;

import com.github.gilbertotcc.fbk.infrastructure.fabrick.FabrickClient;
import com.github.gilbertotcc.fbk.infrastructure.fabrick.models.Transaction;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static java.time.LocalDate.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountTransactionsRetrieverTest {

  private final FabrickClient fabrickClient = mock(FabrickClient.class);

  @Test
  void getBalanceShouldSuccess() {
    String accountId = "accountId";
    List<Transaction> transactions = someTransactionList();
    when(fabrickClient.getTransactionsOfAccount(eq(accountId), any(), any())).thenReturn(Try.success(transactions));

    AccountTransactionRetriever accountTransactionRetriever = new AccountTransactionRetriever(accountId, fabrickClient);
    var accountTransactions = accountTransactionRetriever.getTransactions(now(), now()).get();

    assertEquals(1, accountTransactions.size());
    AccountTransaction firstTransaction = accountTransactions.get(0);
    assertEquals(transactions.get(0).getAmount(), toBigDecimal(firstTransaction.getAmount().getAmount()));
    // ...
    assertEquals(transactions.get(0).getDescription(), firstTransaction.getDescription());
  }

  private List<Transaction> someTransactionList() {
    Transaction.Type type = new Transaction.Type();
    type.setEnumeration("enumeration");

    Transaction transaction = new Transaction();
    transaction.setAccountingDate(now());
    transaction.setAmount(BigDecimal.TEN);
    transaction.setCurrency("EUR");
    transaction.setDescription("A transaction description");
    transaction.setOperationId("operationId");
    transaction.setTransactionId("transactionId");
    transaction.setType(type);
    transaction.setValueDate(now());
    return List.of(transaction);
  }

  private BigDecimal toBigDecimal(int amountInCents) {
    return BigDecimal.valueOf(amountInCents).divide(BigDecimal.valueOf(100), RoundingMode.FLOOR);
  }
}
