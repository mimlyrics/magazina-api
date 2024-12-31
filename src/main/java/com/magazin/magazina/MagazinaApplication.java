package com.magazin.magazina;

import com.magazin.magazina.auth.AuthenticationService;
import com.magazin.magazina.auth.RegisterRequest;
import com.magazin.magazina.models.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.magazin.magazina.models.Role.*;

@SpringBootApplication
public class MagazinaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MagazinaApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service
	) {
		return args -> {
			var admin = RegisterRequest.builder()
					.firstname("Admin")
					.lastname("Admin")
					.email("admin@gmail.com")
					.password("password")
					.role("ADMIN")
					.build();
			System.out.println("ADMIN token: " + service.register(admin).getToken());
			var manager = RegisterRequest.builder()
					.firstname("Manager")
					.lastname("Manager")
					.email("manager@gmail.com")
					.password("password")
					.role("MANAGER")
					.build();
			System.out.println("Manager token: " + service.register(manager).getToken());


		};
	}
}