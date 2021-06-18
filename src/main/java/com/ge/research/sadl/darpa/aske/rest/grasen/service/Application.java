package com.ge.research.sadl.darpa.aske.rest.grasen.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Starts the application when you run either of these commands:
 *
 * <ul>
 *   <li>mvn spring-boot:run</li>
 *   <li>java -jar target/grasen.service-1.0.0-SNAPSHOT.jar</li>
 * </ul>
 */
@SpringBootApplication
//@EnableSwagger2
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
