package com.github.gilbertotcc.fbk.domain.account;

import com.github.gilbertotcc.fbk.domain.common.MonetaryAmount;
import com.github.gilbertotcc.fbk.infrastructure.fabrick.FabrickClient;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AccountBalanceRetriever {

  private final String accountId;

  private final FabrickClient fabrickClient;

  public AccountBalanceRetriever(@Value("${account.id}") String accountId, FabrickClient fabrickClient) {
    this.accountId = accountId;
    this.fabrickClient = fabrickClient;
  }

  public Try<AccountBalance> getBalance() {
    log.info("Retrieve account balance");
    return fabrickClient.getBalanceOfAccount(accountId)
      .map(retrievedBalance -> {
        MonetaryAmount balance = MonetaryAmount.toCentMonetaryAmount(
          retrievedBalance.getBalanceAmount(),
          retrievedBalance.getCurrency());
        MonetaryAmount availableBalance = MonetaryAmount.toCentMonetaryAmount(
          retrievedBalance.getAvailableBalanceAmount(),
          retrievedBalance.getCurrency());

        return AccountBalance.builder()
          .balance(balance)
          .availableBalance(availableBalance)
          .date(retrievedBalance.getDate())
          .build();
      });
  }
}
