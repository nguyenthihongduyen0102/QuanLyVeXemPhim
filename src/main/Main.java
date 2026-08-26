package main;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"controller", "repository"})

public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("--- HỆ THỐNG RẠP CHIẾU PHIM CHO SINH VIÊN ĐANG CHẠY TẠI: http://localhost:8080 ---");
    }
}