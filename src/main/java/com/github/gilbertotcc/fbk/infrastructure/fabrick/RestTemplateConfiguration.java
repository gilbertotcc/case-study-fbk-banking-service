package com.github.gilbertotcc.fbk.infrastructure.fabrick;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Configuration
public class RestTemplateConfiguration {

  @Bean
  public RestTemplate getRestTemplate() {
    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
    mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(
      MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM
    ));
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
    return restTemplate;
  }
}
