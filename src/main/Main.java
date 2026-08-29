package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
        System.out.println("==========================================");
        System.out.println("Backend Rạp phim Server đang chạy tại:");
        System.out.println("");
        System.out.println("==========================================");
    }
}