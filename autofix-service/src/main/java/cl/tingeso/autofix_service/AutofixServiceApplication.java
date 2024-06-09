package cl.tingeso.autofix_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class AutofixServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutofixServiceApplication.class, args);
	}

}
