package com.alura.literalura;

import com.alura.literalura.main.MenuView;
import com.alura.literalura.repositories.AutorRepository;
import com.alura.literalura.repositories.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Autowired
	private LivroRepository repository;

	@Autowired
	private AutorRepository autorRepository;

	@Override
	public void run(String... args) throws Exception {

		MenuView menu = new MenuView(repository, autorRepository);
		menu.showMenu();
	}
}
