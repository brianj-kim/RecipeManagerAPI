package ca.briangroup.recipemanger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RecipeMangerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipeMangerApplication.class, args);
	}

}
