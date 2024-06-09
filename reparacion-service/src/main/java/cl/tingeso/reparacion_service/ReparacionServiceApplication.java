package cl.tingeso.reparacion_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class},
		scanBasePackages={"cl.tingeso.reparacion_service.repositories"})
public class ReparacionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReparacionServiceApplication.class, args);
	}

}
