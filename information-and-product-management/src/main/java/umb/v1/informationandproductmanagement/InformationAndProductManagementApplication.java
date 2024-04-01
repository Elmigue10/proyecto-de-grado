package umb.v1.informationandproductmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class InformationAndProductManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(InformationAndProductManagementApplication.class, args);
	}

}
