package guru.springframework;

import guru.springframework.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


@EnableConfigurationProperties(StorageProperties.class)
@SpringBootApplication//(exclude = {SecurityAutoConfiguration.class })
public class SpringBootOracleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootOracleApplication.class, args);
	}
}
