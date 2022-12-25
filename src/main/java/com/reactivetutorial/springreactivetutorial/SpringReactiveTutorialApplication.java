package com.reactivetutorial.springreactivetutorial;

import com.reactivetutorial.springreactivetutorial.services.FluxAndMonoServices;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringReactiveTutorialApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringReactiveTutorialApplication.class, args);
		FluxAndMonoServices fluxAndMonoServices=new FluxAndMonoServices();
		fluxAndMonoServices.carsFlux()
				.subscribe(fruits-> System.out.println(fruits));
		fluxAndMonoServices.carMono()
				.subscribe(data-> System.out.println(data));
	}

}
