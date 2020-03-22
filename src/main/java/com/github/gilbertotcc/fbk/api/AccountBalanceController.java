package com.github.gilbertotcc.fbk.api;

import com.github.gilbertotcc.fbk.domain.account.AccountBalance;
import com.github.gilbertotcc.fbk.domain.account.AccountBalanceRetriever;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/balance")
@RequiredArgsConstructor
public class AccountBalanceController {

  private final AccountBalanceRetriever accountBalanceRetriever;

  @ApiOperation("Return the account balance.")
  @GetMapping
  public ResponseEntity<Response<AccountBalance>> retrieveAccountBalance() {
    return accountBalanceRetriever.getBalance()
      .map(Response::success)
      .map(ResponseEntity::ok)
      .getOrElse(ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(Response.failure("Cannot retrieve account balance"))
      );
  }
}
