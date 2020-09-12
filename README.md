# Case study: FBK banking service

This Spring Boot service implements few functionalities that make use of the
[Fabrick](https://www.fabrick.com/) API platform.

## Usage

The service needs the following environment variables to be set in order to
properly start and work:

- `ACCOUNT_ID`, the id of the account used by the service;
- `FABRICK_API_KEY`, API key used in the calls to Fabrick APIs.

The following command starts the service *(it needs Java 11)*:

```bash
$ ./gradlew bootRun
```

After starting the service, the operations exposed by its API can be used from
the Swagger web page available at `{BASE_URL}/swagger-ui.html`.

## Test coverage report

The project uses [JaCoco](https://www.eclemma.org/jacoco/) to generate a report
with the tests' coverage.
In order to generate the report run:

```bash
$ ./gradlew jacocoTestReport
```

The HTML report is generated at `build/reports/jacoco/test/html/index.html`.
