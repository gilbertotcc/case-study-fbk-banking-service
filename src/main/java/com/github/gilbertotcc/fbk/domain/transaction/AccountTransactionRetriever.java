package com.github.gilbertotcc.fbk.domain.transaction;

import com.github.gilbertotcc.fbk.domain.common.MonetaryAmount;
import com.github.gilbertotcc.fbk.infrastructure.fabrick.FabrickClient;
import com.github.gilbertotcc.fbk.infrastructure.fabrick.models.Transaction;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AccountTransactionRetriever {

  private final String accountId;

  private final FabrickClient fabrickClient;

  public AccountTransactionRetriever(@Value("${account.id}") String accountId, FabrickClient fabrickClient) {
    this.accountId = accountId;
    this.fabrickClient = fabrickClient;
  }

  public Try<List<AccountTransaction>> getTransactions(LocalDate fromAccountingDate, LocalDate toAccountingDate) {
    log.info(
      "Retrieve transactions from {} to {}",
      fromAccountingDate.format(DateTimeFormatter.ISO_LOCAL_DATE),
      toAccountingDate.format(DateTimeFormatter.ISO_LOCAL_DATE)
    );
    return fabrickClient.getTransactionsOfAccount(accountId, fromAccountingDate, toAccountingDate)
      .map(transactions -> transactions.stream()
        .map(this::toAccountTransaction)
        .collect(Collectors.toList())
    );
  }

  private AccountTransaction toAccountTransaction(Transaction transaction) {
    AccountTransaction.Type type = AccountTransaction.Type.with(
      transaction.getType().getEnumeration(),
      transaction.getType().getValue()
    );
    MonetaryAmount amount = MonetaryAmount.toCentMonetaryAmount(transaction.getAmount(), transaction.getCurrency());
    return AccountTransaction.builder()
      .accountingDate(transaction.getAccountingDate())
      .amount(amount)
      .description(transaction.getDescription())
      .operationId(transaction.getOperationId())
      .transactionId(transaction.getTransactionId())
      .type(type)
      .valueDate(transaction.getValueDate())
      .build();
  }
}
