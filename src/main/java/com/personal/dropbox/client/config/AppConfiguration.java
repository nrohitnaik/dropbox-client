package com.personal.dropbox.client.config;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Scanner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfiguration {

  @Bean
  Scanner getScanner() {
    return new Scanner(System.in);
  }

  @Bean
  public RestTemplate getWebClient() {
    return new RestTemplate();
  }

  @Bean
  public JsonMapper getJsonMapper() {
    return new JsonMapper();
  }

  @Bean
  public Gson getGson() {
    return new GsonBuilder().registerTypeAdapter(LocalDateTime.class,
        (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) ->
                                        ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalDateTime()).create();
  }
}
