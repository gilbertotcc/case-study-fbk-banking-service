package com.github.gilbertotcc.fbk.api;

import com.github.gilbertotcc.fbk.domain.transaction.AccountTransaction;
import com.github.gilbertotcc.fbk.domain.transaction.AccountTransactionRetriever;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class AccountTransactionController {

  private final AccountTransactionRetriever accountTransactionRetriever;

  @ApiOperation("Return the account transactions.")
  @GetMapping
  public ResponseEntity<Response<List<AccountTransaction>>> retrieveAccountTransactions(
    @ApiParam("Start date used for filtering requested transactions.")
    @RequestParam("from")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
    @ApiParam("End date used for filtering requested transactions.")
    @RequestParam("to")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
  ) {
    return accountTransactionRetriever.getTransactions(from, to)
      .map(Response::success)
      .map(ResponseEntity::ok)
      .getOrElse(ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(Response.failure("Cannot submit transfer"))
      );
  }
}
