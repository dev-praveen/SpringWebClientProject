package com.rest.client.controllers;

import com.rest.client.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import javax.mail.MessagingException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/web")
public class WebClientController {

  private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

  @Autowired private WebClient webClient;

  //@Autowired private EmailUtil emailUtil;

  @Bean
  public RestTemplate getRestTemplate() {
    return new RestTemplate();
  }

  @Bean
  public WebClient getWebClientBuilder() {
    return WebClient.builder()
        .exchangeStrategies(
            ExchangeStrategies.builder()
                .codecs(
                    configurator -> configurator.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                .build())
        .build();
  }

  @GetMapping(value = "/comments", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<CommentsResponse> getComments() {

    log.debug("Entered in getComments method");
    try {
      CommentsResponse commentsResponse = new CommentsResponse();
      RestTemplate restTemplate = getRestTemplate();
      log.debug("Calling external service using RestTemplate " + BASE_URL);
      Comments[] comments = restTemplate.getForObject(BASE_URL + "/comments", Comments[].class);
      final List<Comments> commentsList = Arrays.asList(comments);
      commentsResponse.setCommentsList(commentsList);
      return ResponseEntity.ok(commentsResponse);
    } catch (RestClientException exception) {
      log.error("RestTemplate call is failed with " + exception.getMessage());
    }
    return null;
  }

  @GetMapping(value = "/photos", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<PhotosResponse> getPhotos() {

    log.debug("Entered in getPhotos method");
    try {
      PhotosResponse photosResponse = new PhotosResponse();
      RestTemplate restTemplate = getRestTemplate();
      log.debug("Calling external service using RestTemplate " + BASE_URL);
      // photos = restTemplate.getForObject(BASE_URL+"/photos", Photos[].class);
      Photos[] photos =
          webClient.get().uri(BASE_URL + "/photos").retrieve().bodyToMono(Photos[].class).block();
      final List<Photos> photosList = Arrays.asList(photos);
      photosResponse.setPhotosList(photosList);
      return ResponseEntity.ok(photosResponse);
    } catch (RestClientException exception) {
      log.error("RestTemplate call is failed with " + exception.getMessage());
    }
    return null;
  }

  @GetMapping(value = "/todos", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<TodosResponse> getTodosWithClient() {

    log.debug("Entered in getTodosWithClient method");
    try {
      TodosResponse todosResponse = new TodosResponse();
      Todos[] todos =
          WebClient.create(BASE_URL)
              .get()
              .uri("/todos")
              .retrieve()
              .bodyToMono(Todos[].class)
              .block();
      final List<Todos> todosList = Arrays.asList(todos);
      todosResponse.setTodos(todosList);
      return ResponseEntity.ok(todosResponse);
    } catch (RestClientException exception) {
      log.error("RestTemplate call is failed with " + exception.getMessage());
    }
    return null;
  }

  /*@GetMapping(value = "/email")
  public void sendEmail() throws MessagingException {
    emailUtil.sendEmail();
  }*/
}
