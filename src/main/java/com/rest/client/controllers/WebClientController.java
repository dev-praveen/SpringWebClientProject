package com.rest.client.controllers;

import com.rest.client.models.Comments;
import com.rest.client.models.Photos;
import com.rest.client.models.Todos;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@RestController
@RequestMapping("/web")
public class WebClientController {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    /*@Autowired
    private WebClient webClient = WebClient.create(BASE_URL);*/

    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    /*@Bean
    public WebClient getWebClientBuilder(){
        return   WebClient.builder().exchangeStrategies(ExchangeStrategies.builder()
                .codecs(configurator -> configurator
                        .defaultCodecs()
                        .maxInMemorySize(16 * 1024 * 1024))
                .build())
                .build();
    }*/

    @GetMapping(value = "/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comments[]> getComments() {
        log.debug("Entered in getComments method");
        RestTemplate restTemplate = getRestTemplate();
        Comments[] comments = null;
        try {
            log.debug("Calling external service using RestTemplate "+BASE_URL);
            comments = restTemplate.getForObject(BASE_URL+"/comments", Comments[].class);
        }catch (HttpStatusCodeException exception) {
            log.error("RestTemplate call is failed with "+exception.getResponseBodyAsString());
        }
        return ResponseEntity.ok(comments);
    }

    @GetMapping(value = "/photos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Photos[]> getPhotos() {
        log.debug("Entered in getPhotos method");
        RestTemplate restTemplate = getRestTemplate();
        Photos[] photos = null;
        try {
            log.debug("Calling external service using RestTemplate "+BASE_URL);
            //photos = restTemplate.getForObject(BASE_URL+"/photos", Photos[].class);
            photos = WebClient.create(BASE_URL)
                    .get().uri("/photos")
                    .retrieve()
                    .bodyToMono(Photos[].class)
                    .block();
        }catch (HttpStatusCodeException exception) {
            log.error("RestTemplate call is failed with "+exception.getResponseBodyAsString());
        }
        return ResponseEntity.ok(photos);
    }

    @GetMapping(value = "/todos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Todos[]> getTodosWithClient() {

        log.debug("Entered in getTodosWithClient method");
        Todos[] todos = null;
        try {
            todos = WebClient.create(BASE_URL)
                    .get().uri("/todos")
                    .retrieve()
                    .bodyToMono(Todos[].class)
                    .block();
        }catch (HttpStatusCodeException exception) {
            log.error("RestTemplate call is failed with "+exception.getResponseBodyAsString());
        }
        return ResponseEntity.ok(todos);
    }
}
