package com.reactivetutorial.springreactivetutorial.services;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

@Service
public class FluxAndMonoServices {

    public Flux<String> carsFlux(){
        return Flux.fromIterable(List.of("Audi","BMW","Benz")).log();
    }

    public Mono<String> carMono(){
        return Mono.just("BMW").log();
    }

    /**
     * flatMap operator
     * @return
     */
    public Mono<List<String>> carMonoFlatMap(){
        return Mono.just("BMW")
                .flatMap(s->Mono.just(List.of(s.split(""))))
                .log();
    }

    /***
     * convert Mono to Flux
     * flatMapMany
     * @return
     */
    public Flux<String> carMonoFlatMapMany(){
        return Mono.just("BMW")
                .flatMapMany(s->Flux.just(s.split("")))
                .log();
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

    /***
     * use Functional Interface
     * Transform operator
     * @return
     */
    public Flux<String> carsFluxTransform(int length){

        Function<Flux<String>,Flux<String>> filterCars=data->data.filter(s->s.length()>length);
        return Flux.fromIterable(List.of("Audi","BMW","Benz"))
                .map(String::toUpperCase)
                .transform(filterCars)
                .log();
    }

    /***
     * defaultIfEmpty
     * @param length
     * @return
     */
    public Flux<String> carsFluxTransformDefaultIfEmpty(int length){

        Function<Flux<String>,Flux<String>> filterCars=data->data.filter(s->s.length()>length);
        return Flux.fromIterable(List.of("Audi","BMW","Benz"))
                .map(String::toUpperCase)
                .transform(filterCars)
                .defaultIfEmpty("default")
                .log();
    }

    /**
     *
     * @param length
     * @return
     */
    public Flux<String> carsFluxTransformSwitchIfEmpty(int length){

        Function<Flux<String>,Flux<String>> filterCars=data->data.filter(s->s.length()>length);
        return Flux.fromIterable(List.of("Audi","BMW","Benz"))
                .map(String::toUpperCase)
                .transform(filterCars)
                .switchIfEmpty(Flux.fromIterable(List.of("Hyundai","Suzuki","Ford")))
                .log();
    }

    /***
     * FlatMap operator
     * @return
     */
    public Flux<String> carsFluxFlatMap(){
        return Flux.fromIterable(List.of("Audi","BMW","Benz"))
                .flatMap(s->Flux.just(s.split("")))
                .log();
    }

    public Flux<String> carsFluxFlatMapAsync(){
        return Flux.fromIterable(List.of("Audi","BMW","Benz"))
                .flatMap(s->Flux.just(s.split("")))
                .delayElements(Duration.ofMillis(new Random().nextInt(1000)))
                .log();
    }

    /***
     * maintains order
     * ConcatMap
     * @return
     */
    public Flux<String> carsFluxConcatMapAsync(){
        return Flux.fromIterable(List.of("Audi","BMW","Benz"))
                .concatMap(s->Flux.just(s.split("")))
                .delayElements(Duration.ofMillis(new Random().nextInt(1000)))
                .log();
    }

    /**
     * sequential order
     * concat
     * @return
     */
    public Flux<String> carsFluxConcat(){

        var luxCars=Flux.just("Audi","BMW");
        var cars=Flux.just("Kia","Suzuki");

        return Flux.concat(luxCars,cars).log();
    }

    /**
     * concatWith operator
     * @return
     */
    public Flux<String> carsMonoConcatWith(){
        var luxCars=Mono.just("Audi");
        var cars=Mono.just("Kia");

        return luxCars.concatWith(cars);
    }

    /**
     * merge operator - emits data async from publishers
     * @return
     */
    public Flux<String> carsFluxMerge(){
        var luxCars=Flux.just("Audi","BMW").delayElements(Duration.ofMillis(60));
        var cars=Flux.just("Kia","Suzuki").delayElements(Duration.ofMillis(40));

        return Flux.merge(luxCars,cars).log();
    }

    /**
     * mergeSequential - data emits sequentially
     * @return
     */
    public Flux<String> carsFluxMergeWithSequential(){
        var luxCars=Flux.just("Audi","BMW").delayElements(Duration.ofMillis(60));
        var cars=Flux.just("Kia","Suzuki").delayElements(Duration.ofMillis(40));

        return Flux.mergeSequential(luxCars,cars).log();
    }

    /**
     * Zip Operator - combine data from multiple publisher
     * at max 8 publishers can be used
     * @return
     */
    public Flux<String> carsFluxZip(){
        var luxCars=Flux.just("Audi","BMW").delayElements(Duration.ofMillis(60));
        var cars=Flux.just("Kia","Suzuki").delayElements(Duration.ofMillis(40));

        return Flux.zip(luxCars,cars,(first,second)-> first+second).log();
    }

    /**
     * zipwith tuple
     * @return
     */
    public Flux<String> carsFluxZipTuple(){
        var luxCars=Flux.just("Audi","BMW").delayElements(Duration.ofMillis(60));
        var cars=Flux.just("Kia","Suzuki").delayElements(Duration.ofMillis(40));
        var evs=Flux.just("Tesla","Volvo").delayElements(Duration.ofMillis(40));

        return Flux.zip(luxCars,cars,evs)
                .map(objects -> objects.getT1()+objects.getT2()+objects.getT3());
    }


    /**
     * zip - Mono
     * @return
     */
    public Mono<String> carsMonoZipTuple(){
        var luxCars=Mono.just("Audi").delayElement(Duration.ofMillis(60));
        var cars=Mono.just("Kia").delayElement(Duration.ofMillis(40));
        var evs=Mono.just("Tesla").delayElement(Duration.ofMillis(40));

        return Mono.zip(luxCars,cars,evs)
                .map(objects -> objects.getT1()+objects.getT2()+objects.getT3());
    }
}
