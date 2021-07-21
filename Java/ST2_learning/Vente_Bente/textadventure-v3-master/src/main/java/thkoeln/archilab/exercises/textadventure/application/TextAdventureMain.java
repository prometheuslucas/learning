package thkoeln.archilab.exercises.textadventure.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("thkoeln.archilab.exercises.textadventure.*")
@EntityScan("thkoeln.archilab.exercises.textadventure.*")
public class TextAdventureMain implements CommandLineRunner {
	@Autowired
	private Game game;

	public static void main(String[] args) {
		SpringApplication.run(TextAdventureMain.class, args);
	}

	@Override
	public void run(String... args) {
		game.play();
	}
}
