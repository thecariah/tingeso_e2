package cl.tingeso.historial_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class},
		scanBasePackages={"cl.tingeso.historial_service.repositories"})
@EnableEurekaClient
public class HistorialServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HistorialServiceApplication.class, args);
	}

}
