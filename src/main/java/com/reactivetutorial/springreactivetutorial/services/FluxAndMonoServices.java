package com.reactivetutorial.springreactivetutorial.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class FluxAndMonoServices {

    public Flux<String> carsFlux(){
        return Flux.fromIterable(List.of("Audi","BMW","Benz")).log();
    }

    public Mono<String> carMono(){
        return Mono.just("BMW").log();
    }

}
