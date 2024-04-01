package tgid.transactional.transactionalService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Transactional Service", version = "1", description = "Transactional service api for Tgid"))
public class TransactionalServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionalServiceApplication.class, args);
	}

}
