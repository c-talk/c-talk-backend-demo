package me.a632079.ctalk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication
public class CTalkApplication {

	public static void main(String[] args) {
		SpringApplication.run(CTalkApplication.class, args);
	}

}
