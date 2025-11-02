package io.github.magwas.coder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.map.repository.config.EnableMapRepositories;
import org.springframework.stereotype.Component;

import io.github.magwas.konveyor.annotations.Glue;

@Configuration
@ComponentScan("io.github.magwas")
@EnableMapRepositories("io.github.magwas.coder")
@Component
@Glue
public class CoderApplication implements CommandLineRunner {
	@Autowired
	MainLoopService mainLoopService;

	public static void main(String[] args) throws Exception {
		ApplicationContext springContext = new AnnotationConfigApplicationContext(
				CoderApplication.class, com.fasterxml.jackson.databind.ObjectMapper.class);
		CoderApplication application = springContext.getBean(CoderApplication.class);
		application.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		mainLoopService.apply();
	}
}
