package cl.tingeso.vehiculo_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class},
		scanBasePackages={"cl.tingeso.vehiculo_service.repositories"})
public class VehiculoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehiculoServiceApplication.class, args);
	}

}