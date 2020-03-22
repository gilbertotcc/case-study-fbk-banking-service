package com.github.gilbertotcc.fbk.api;

import com.github.gilbertotcc.fbk.domain.transfer.SubmitTransferInput;
import com.github.gilbertotcc.fbk.domain.transfer.Transfer;
import com.github.gilbertotcc.fbk.domain.transfer.TransferSubmitter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfers")
@RequiredArgsConstructor
public class TransferController {

  private final TransferSubmitter transferSubmitter;

  @PostMapping
  public ResponseEntity<Response<Transfer>> submitTransfer(@RequestBody SubmitTransferInput submitTransferInput) {
    return transferSubmitter.submitTransfer(submitTransferInput)
      .map(Response::success)
      .map(ResponseEntity::ok)
      .getOrElse(ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(Response.failure("Cannot retrieve account transactions"))
      );
  }
}
