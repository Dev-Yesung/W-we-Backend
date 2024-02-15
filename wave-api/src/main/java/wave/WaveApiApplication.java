package wave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WaveApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaveApiApplication.class, args);
	}

}
