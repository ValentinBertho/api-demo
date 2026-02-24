package fr.mismo.demo_web_addin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoWebAddinApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoWebAddinApplication.class, args);
		System.out.println("\n" +
				"╔════════════════════════════════════════════════╗\n" +
				"║   🚀 ATHENEO Demo API démarrée avec succès !  ║\n" +
				"║                                                ║\n" +
				"║   📡 API : http://localhost:8080/atheneo      ║\n" +
				"║   📚 Swagger : /swagger-ui.html               ║\n" +
				"║   🗄️  H2 Console : /h2-console                ║\n" +
				"╚════════════════════════════════════════════════╝\n");
	}
}