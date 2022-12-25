package com.reactivetutorial.springreactivetutorial.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

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
}