package com.github.gilbertotcc.fbk.infrastructure.fabrick;

import com.github.gilbertotcc.fbk.infrastructure.fabrick.models.Balance;
import com.github.gilbertotcc.fbk.infrastructure.fabrick.models.CreateTransferRequestBody;
import com.github.gilbertotcc.fbk.infrastructure.fabrick.models.CreateTransferResponse;
import com.github.gilbertotcc.fbk.infrastructure.fabrick.models.GetBalanceResponse;
import com.github.gilbertotcc.fbk.infrastructure.fabrick.models.GetTransactionsResponse;
import com.github.gilbertotcc.fbk.infrastructure.fabrick.models.Transaction;
import com.github.gilbertotcc.fbk.infrastructure.fabrick.models.Transfer;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.lang.String.format;

@Slf4j
@Component
public class FabrickClient {

  private static final String GET_BALANCE_ACCOUNT_URL = "/api/gbs/banking/v4.0/accounts/%s/balance";
  private static final String GET_TRANSACTIONS_ACCOUNT_URL =
    "/api/gbs/banking/v4.0/accounts/%s/transactions?fromAccountingDate=%s&toAccountingDate=%s";
  private static final String CREATE_TRANSFER_URL = "/api/gbs/banking/v4.0/accounts/%s/payments/money-transfers";

  private static final String HEADER_API_KEY = "Api-Key";
  private static final String HEADER_AUTH_SCHEMA = "Auth-Schema";
  private static final String HEADER_AUTH_SCHEMA_VALUE = "S2S";

  private final String baseUrl;

  private final String apiKey;

  private final RestTemplate restTemplate;

  public FabrickClient(@Value("{fabrick.baseUrl}") String baseUrl,
                       @Value("{fabrick.apiKey}") String apiKey,
                       RestTemplate restTemplate) {
    this.baseUrl = baseUrl;
    this.apiKey = apiKey;
    this.restTemplate = restTemplate;
  }

  public Try<Balance> getBalanceOfAccount(String accountId) {
    log.info("Retrieve balance of account {}", accountId);
    RequestEntity<Void> request = RequestEntity
      .get(createAbsoluteUriWith(format(GET_BALANCE_ACCOUNT_URL, accountId)))
      .header(HEADER_AUTH_SCHEMA, HEADER_AUTH_SCHEMA_VALUE)
      .header(HEADER_API_KEY, apiKey)
      .build();
    return Try.ofSupplier(() -> restTemplate.exchange(request, GetBalanceResponse.class))
      .filterTry(response -> response.getStatusCode().is2xxSuccessful(), FabrickFailureException::new)
      .map(response -> response.getBody().getPayload());
  }

  public Try<List<Transaction>> getTransactionsOfAccount(String accountId,
                                                         LocalDate fromAccountingDate,
                                                         LocalDate toAccountingDate) {
    log.info("Retrieve transactions of account {}", accountId);
    String fromAccountingDateString = fromAccountingDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    String toAccountingDateString = toAccountingDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    RequestEntity<Void> request = RequestEntity
      .get(createAbsoluteUriWith(
        format(GET_TRANSACTIONS_ACCOUNT_URL, accountId, fromAccountingDateString, toAccountingDateString))
      )
      .header(HEADER_AUTH_SCHEMA, HEADER_AUTH_SCHEMA_VALUE)
      .header(HEADER_API_KEY, apiKey)
      .build();
    return Try.ofSupplier(() -> restTemplate.exchange(request, GetTransactionsResponse.class))
      .filterTry(response -> response.getStatusCode().is2xxSuccessful(), FabrickFailureException::new)
      .map(response -> response.getBody().getPayload().getList());
  }

  public Try<Transfer> createTransferWithAccount(String accountId, CreateTransferRequestBody transferRequestBody) {
    RequestEntity<CreateTransferRequestBody> request = RequestEntity
      .post(createAbsoluteUriWith(format(CREATE_TRANSFER_URL, accountId)))
      .header(HEADER_AUTH_SCHEMA, HEADER_AUTH_SCHEMA_VALUE)
      .header(HEADER_API_KEY, apiKey)
      .body(transferRequestBody);
    return Try.ofSupplier(() -> restTemplate.exchange(request, CreateTransferResponse.class))
      .filterTry(response -> response.getStatusCode().is2xxSuccessful(), FabrickFailureException::new)
      .map(response -> response.getBody().getPayload());
  }

  private URI createAbsoluteUriWith(String endpointUrl) {
    return URI.create(baseUrl + endpointUrl);
  }
}
