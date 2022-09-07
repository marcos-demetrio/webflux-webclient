package com.example.webclient;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@ReactiveFeignClient(value = "movieService", url = "${movie.service.url}")
public interface MovieClient {

  @GetMapping("movies")
  Flux<MovieDto> findAll();

  @GetMapping("movies/{movieId}")
  Mono<MovieDto> findById(@PathVariable("movieId") Integer movieId);

  @PostMapping("movies")
  Mono<MovieDto> save(MovieDto movieDto);

  @PutMapping("movies/{movieId}")
  Mono<Void> update(@PathVariable("movieId") Integer movieId, MovieDto movieDto);

  @DeleteMapping("movies/{movieId}")
  Mono<Void> delete(@PathVariable("movieId") Integer movieId);
}
