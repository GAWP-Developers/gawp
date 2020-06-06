package com.gawpdevelopers.gawp;

import com.gawpdevelopers.gawp.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@EnableConfigurationProperties(StorageProperties.class)
@SpringBootApplication//(exclude = {SecurityAutoConfiguration.class })
public class GawpApplication {

	public static void main(String[] args) {
		SpringApplication.run(GawpApplication.class, args);
	}
}
