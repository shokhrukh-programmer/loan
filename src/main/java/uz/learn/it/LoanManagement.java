package uz.learn.it;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LoanManagement {
    public static void main(String[] args) {
        SpringApplication.run(LoanManagement.class, args);
    }
}