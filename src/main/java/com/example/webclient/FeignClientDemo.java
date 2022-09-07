package com.example.webclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class FeignClientDemo implements CommandLineRunner {

  @Autowired private MovieClient movieClient;

  @Override
  public void run(String... args) throws Exception {
    Flux.concat(getAll(), post(), get(), put(), get(), delete()).subscribe();
  }

  private Mono<Void> getAll() {
    return this.movieClient
        .findAll()
        .map(MovieDto::toString)
        .doOnNext(log::info)
        .doFinally(s -> log.info("------------- GET All completed ------------------"))
        .then();
  }

  private Mono<Void> post() {
    MovieDto dto = MovieDto.create(5, "Harry Potter and the Sorcerer Stone", 1999, 7.6);
    return this.movieClient
        .save(dto)
        .doFinally(s -> log.info("------------- POST Movie completed ------------------"))
        .then();
  }

  private Mono<Void> put() {
    MovieDto dto = MovieDto.create(null, "Harry Potter and the Sorcerer Stone", 2001, 7.6);
    return this.movieClient
        .update(5, dto)
        .doFinally(s -> log.info("------------- Movie updated ------------------"))
        .then();
  }

  private Mono<Void> get() {
    return this.movieClient
        .findById(5)
        .map(MovieDto::toString)
        .doOnNext(log::info)
        .doFinally(s -> log.info("------------- GET Movie completed ------------------"))
        .then();
  }

  private Mono<Void> delete() {
    return this.movieClient
        .delete(5)
        .doFinally(s -> log.info("------------- Movie deleted ------------------"))
        .then();
  }
}
