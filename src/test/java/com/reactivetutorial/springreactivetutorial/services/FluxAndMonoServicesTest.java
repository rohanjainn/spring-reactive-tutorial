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

    @Test
    void carsFluxConcat() {
        StepVerifier.create(fluxAndMonoServices.carsFluxConcat())
                .expectNext("Audi","BMW","Kia","Suzuki")
                .verifyComplete();
    }

    @Test
    void carsMonoConcatWith() {
        StepVerifier.create(fluxAndMonoServices.carsMonoConcatWith())
                .expectNext("Audi","Kia")
                .verifyComplete();
    }

    @Test
    void carsMonoMerge() {
        StepVerifier.create(fluxAndMonoServices.carsFluxMerge())
                .expectNextCount(4)
                .verifyComplete();
    }

    @Test
    void carsMonoMergeWithSequential() {
        StepVerifier.create(fluxAndMonoServices.carsFluxMergeWithSequential())
                .expectNext("Audi","BMW","Kia","Suzuki")
                .verifyComplete();
    }

    @Test
    void carsMonoZip() {
        StepVerifier.create(fluxAndMonoServices.carsFluxZip())
                .expectNext("AudiKia","BMWSuzuki")
                .verifyComplete();
    }

    @Test
    void carsMonoZipTuple() {
        StepVerifier.create(fluxAndMonoServices.carsFluxZipTuple())
                .expectNext("AudiKiaTesla","BMWSuzukiVolvo")
                .verifyComplete();
    }

    @Test
    void testCarsMonoZipTuple() {
        StepVerifier.create(fluxAndMonoServices.carsMonoZipTuple())
                .expectNext("AudiKiaTesla")
                .verifyComplete();
    }

    @Test
    void carsFluxOnErrorReturn() {
        StepVerifier.create(fluxAndMonoServices.carsFluxOnErrorReturn())
                .expectNext("Audi","Bmw","Cars")
                .verifyComplete();
    }

    @Test
    void carsFluxOnErrorContinue() {
        StepVerifier.create(fluxAndMonoServices.carsFluxOnErrorContinue())
                .expectNext("BMW")
                .verifyComplete();
    }

    @Test
    void carsFluxOnErrorMap() {
        StepVerifier.create(fluxAndMonoServices.carsFluxOnErrorMap())
                .expectNext("AUDI")
                .expectError(IllegalStateException.class)
                .verify();
    }

    @Test
    void carsFluxDoOnError() {
        StepVerifier.create(fluxAndMonoServices.carsFluxDoOnError())
                .expectNext("AUDI")
                .expectError(RuntimeException.class)
                .verify();
    }
}