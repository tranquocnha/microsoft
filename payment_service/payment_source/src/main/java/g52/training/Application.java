package g52.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.ZonedDateTime;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println(now);
    }
}
