# Case study: FBK banking service

# Start the service

In order to start the Spring Boot service the following environment variables
must be set:

- `ACCOUNT_ID`, it is the id of the account used by the service;
- `FABRICK_API_KEY`, API key used in the calls to Fabrick APIs.

Then the service can be ran with:

```bash
$ ./gradlew bootRun
```

*(Java 11 is needed.)*

# Testing the service

The operations of the service can be tested using the Swagger web page available
at `{BASE_URL}/swagger-ui.html`.

