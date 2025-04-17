package com.bank.antifraud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.bank.antifraud", "com.bank.antifraud.mappers"})
public class AntifraudApplication {
    public static void main(String[] args) {

        SpringApplication.run(AntifraudApplication.class, args);

    }
}
