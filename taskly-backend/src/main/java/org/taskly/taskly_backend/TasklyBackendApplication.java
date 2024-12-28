package org.taskly.taskly_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class TasklyBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(TasklyBackendApplication.class, args);
	}

}
