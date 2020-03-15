package com.github.gilbertotcc.fbk.infrastructure.fabrick;

import com.github.gilbertotcc.fbk.infrastructure.fabrick.models.CreateTransferRequestBody;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.SocketUtils;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.stream.Collectors;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FabrickClientTest {

  private static final int PORT = SocketUtils.findAvailableTcpPort();
  private static final String BASE_URL = format("http://localhost:%d", PORT);

  private static final String ACCOUNT_ID = "accountId";
  private static final String API_KEY = "apiKey";

  private static final RestTemplate REST_TEMPLATE = new RestTemplateConfiguration().getRestTemplate();

  private WireMockServer wireMockServer;

  @BeforeEach
  void setup() {
    wireMockServer = new WireMockServer(PORT);
    wireMockServer.start();
  }

  @AfterEach
  void teardown() {
    wireMockServer.stop();
  }

  @Test
  void getBalanceOfAccountShouldSuccess() throws IOException {
    final String response = loadJsonFromFile("/fabrick_models/get_account_balance_response.json");
    final String testUrl = format("/api/gbs/banking/v4.0/accounts/%s/balance", ACCOUNT_ID);
    wireMockServer.stubFor(get(urlEqualTo(testUrl))
      .withHeader("Api-Key", equalTo(API_KEY))
      .willReturn(aResponse().withBody(response)));

    FabrickClient client = new FabrickClient(BASE_URL, API_KEY, REST_TEMPLATE);
    var balance = client.getBalanceOfAccount(ACCOUNT_ID).get();

    // One assertion for the sake of simplicity
    assertEquals(new BigDecimal("740.27"), balance.getBalanceAmount());
  }

  @Test
  void getTransactionsOfAccountShouldSuccess() throws IOException {
    final String response = loadJsonFromFile("/fabrick_models/get_transactions_response.json");
    final String testUrl = format("/api/gbs/banking/v4.0/accounts/%s/transactions", ACCOUNT_ID);
    wireMockServer.stubFor(get(urlPathMatching(testUrl))
      .withQueryParam("fromAccountingDate", equalTo("2019-10-31"))
      .withQueryParam("toAccountingDate", equalTo("2020-03-14"))
      .withHeader("Api-Key", equalTo(API_KEY))
      .willReturn(aResponse().withBody(response)));

    FabrickClient client = new FabrickClient(BASE_URL, API_KEY, REST_TEMPLATE);
    var transactions = client
      .getTransactionsOfAccount(ACCOUNT_ID, LocalDate.of(2019, 10, 31), LocalDate.of(2020, 3, 14))
      .get();

    assertFalse(transactions.isEmpty());
    var firstTransaction = transactions.get(0);
    assertEquals("1520405321", firstTransaction.getTransactionId());
  }

  @Test
  void createTransferWithAccountShouldFail() throws IOException {
    final String response = loadJsonFromFile("/fabrick_models/create_transfer_error_response.json");
    final String testUrl = format("/api/gbs/banking/v4.0/accounts/%s/payments/money-transfers", ACCOUNT_ID);
    wireMockServer.stubFor(post(urlEqualTo(testUrl))
      .withHeader("Api-Key", equalTo(API_KEY))
      .willReturn(aResponse().withBody(response).withStatus(503)));

    var createTransferRequestBody = CreateTransferRequestBody.builder()
      .creditor(CreateTransferRequestBody.Creditor.of(
        "creditorName",
        CreateTransferRequestBody.CreditorAccount.of("accountCode")))
      .amount(new BigDecimal("0.01"))
      .currency("EUR")
      .executionDate(LocalDate.now())
      .description("My transfer description")
      .build();

    FabrickClient client = new FabrickClient(BASE_URL, API_KEY, REST_TEMPLATE);
    var newTransferTry = client.createTransferWithAccount(ACCOUNT_ID, createTransferRequestBody);

    assertTrue(newTransferTry.isFailure());
  }

  static String loadJsonFromFile(final String file) throws IOException {
    try (BufferedReader reader = new BufferedReader(
      new InputStreamReader(FabrickClientTest.class.getResourceAsStream(file), StandardCharsets.UTF_8))
    ) {
      return reader.lines().collect(Collectors.joining("\n"));
    } catch (IOException e) {
      throw new IOException(String.format("Error occurred while loading/reading file %s", file), e);
    }
  }
}
