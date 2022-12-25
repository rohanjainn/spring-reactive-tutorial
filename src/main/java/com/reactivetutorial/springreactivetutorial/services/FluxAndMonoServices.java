package com.reactivetutorial.springreactivetutorial.services;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class FluxAndMonoServices {

    public Flux<String> carsFlux(){
        return Flux.fromIterable(List.of("Audi","BMW","Benz")).log();
    }

    public Mono<String> carMono(){
        return Mono.just("BMW").log();
    }

    /**
     * Map Operator
     * @return
     */
    public Flux<String> carsFluxMap(){
        return Flux.fromIterable(List.of("Audi","BMW","Benz"))
                .map(String::toUpperCase)
                .log();
    }

    /**
     * Filter operator
     * @return
     */
    public Flux<String> carsFluxFilter(){
        return Flux.fromIterable(List.of("Audi","BMW","Benz"))
                .map(String::toUpperCase)
                .filter(str->str.length()>3)
                .log();
    }

}
