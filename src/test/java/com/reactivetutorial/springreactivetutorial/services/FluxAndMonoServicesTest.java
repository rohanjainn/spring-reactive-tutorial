package com.reactivetutorial.springreactivetutorial.services;

import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class FluxAndMonoServicesTest {

    FluxAndMonoServices fluxAndMonoServices=new FluxAndMonoServices();
    @Test
    void carsFlux() {

        StepVerifier.create(fluxAndMonoServices.carsFlux())
                .expectNext("Audi","BMW","Benz")
                .verifyComplete();
    }

    @Test
    void carMono() {
        StepVerifier.create(fluxAndMonoServices.carMono())
                .expectNext("BMW")
                .verifyComplete();
    }

    @Test
    void carsFluxMap() {
        StepVerifier.create(fluxAndMonoServices.carsFluxMap())
                .expectNext("AUDI","BMW","BENZ")
                .verifyComplete();
    }

    @Test
    void carsFluxFilter() {
        StepVerifier.create(fluxAndMonoServices.carsFluxFilter())
                .expectNext("AUDI","BENZ")
                .verifyComplete();
    }

    @Test
    void carMonoFlatMap() {
        StepVerifier.create(fluxAndMonoServices.carMonoFlatMap())
                .expectNextCount(1)
                .verifyComplete();

    }

    @Test
    void carsFluxFlatMapAsync() {
        String s="AudiBMWBenz";
        StepVerifier.create(fluxAndMonoServices.carsFluxFlatMapAsync())
                .expectNextCount(s.length())
                .verifyComplete();
    }

    @Test
    void carsFluxConcatMapAsync() {
        String s="AudiBMWBenz";
        StepVerifier.create(fluxAndMonoServices.carsFluxFlatMapAsync())
                .expectNextCount(s.length())
                .verifyComplete();
    }

    @Test
    void carMonoFlatMapMany() {
        String s="BMW";
        StepVerifier.create(fluxAndMonoServices.carMonoFlatMapMany())
                .expectNextCount(s.length())
                .verifyComplete();
    }

    @Test
    void carsFluxTransform() {
        StepVerifier.create(fluxAndMonoServices.carsFluxTransform(3))
                .expectNext("AUDI","BENZ")
                .verifyComplete();
    }

    @Test
    void carsFluxTransformDefaultIfEmpty() {
        StepVerifier.create(fluxAndMonoServices.carsFluxTransformDefaultIfEmpty(5))
                .expectNext("default")
                .verifyComplete();
    }

    @Test
    void carsFluxTransformSwitchIfEmpty() {
        StepVerifier.create(fluxAndMonoServices.carsFluxTransformSwitchIfEmpty(5))
                .expectNext("Hyundai","Suzuki","Ford")
                .verifyComplete();
    }
}