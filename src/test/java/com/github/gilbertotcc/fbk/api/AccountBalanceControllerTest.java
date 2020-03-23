package com.github.gilbertotcc.fbk.api;

import com.github.gilbertotcc.fbk.domain.balance.AccountBalance;
import com.github.gilbertotcc.fbk.domain.balance.AccountBalanceRetriever;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountBalanceControllerTest {

  private final AccountBalanceRetriever accountBalanceRetriever = mock(AccountBalanceRetriever.class);

  @Test
  void retrieveAccountBalanceShouldSuccess() {
    AccountBalance accountBalance = someAccountBalance();
    when(accountBalanceRetriever.getBalance()).thenReturn(Try.success(accountBalance));

    AccountBalanceController controller = new AccountBalanceController(accountBalanceRetriever);

    var responseEntity = controller.retrieveAccountBalance();

    assertEquals(accountBalance, responseEntity.getBody().getPayload());
  }

  private AccountBalance someAccountBalance() {
    return AccountBalance.builder()
      .build();
  }
}
