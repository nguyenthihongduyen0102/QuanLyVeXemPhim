package main;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = {
        "handler",
        "service",
        "repository",
        "model",
        "policy",
        "utils"
})
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner printFrontendUrl() {
        return args -> {
            System.out.println("==========================================");
            System.out.println("Frontend chạy tại:");
            System.out.println("https://nguyenthihongduyen0102.github.io/QuanLyVeXemPhim/frontend/");
            System.out.println("==========================================");
        };
    }
}