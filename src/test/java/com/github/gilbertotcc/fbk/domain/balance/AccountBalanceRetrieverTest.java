package com.github.gilbertotcc.fbk.domain.balance;

import com.github.gilbertotcc.fbk.infrastructure.fabrick.FabrickClient;
import com.github.gilbertotcc.fbk.infrastructure.fabrick.models.Balance;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountBalanceRetrieverTest {

  private final FabrickClient fabrickClient = mock(FabrickClient.class);

  @Test
  void getBalanceShouldSuccess() {
    String accountId = "accountId";
    Balance balance = someBalance();
    when(fabrickClient.getBalanceOfAccount(eq(accountId))).thenReturn(Try.success(balance));

    AccountBalanceRetriever accountBalanceRetriever = new AccountBalanceRetriever(accountId, fabrickClient);
    var accountBalance = accountBalanceRetriever.getBalance().get();

    assertEquals(balance.getBalanceAmount(), toBigDecimal(accountBalance.getBalance().getAmount()));
    assertEquals(balance.getCurrency(), accountBalance.getBalance().getCurrency());
    assertEquals(balance.getAvailableBalanceAmount(), toBigDecimal(accountBalance.getAvailableBalance().getAmount()));
    assertEquals(balance.getCurrency(), accountBalance.getAvailableBalance().getCurrency());
    assertEquals(balance.getDate(), accountBalance.getDate());
  }

  private Balance someBalance() {
    Balance balance = new Balance();
    balance.setBalanceAmount(BigDecimal.ZERO);
    balance.setAvailableBalanceAmount(BigDecimal.ZERO);
    balance.setCurrency("EUR");
    balance.setDate(LocalDate.now());
    return balance;
  }

  private BigDecimal toBigDecimal(int amountInCents) {
    return BigDecimal.valueOf(amountInCents).divide(BigDecimal.valueOf(100), RoundingMode.FLOOR);
  }
}
