package com.okankoca.pf2currencysim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Pf2CurrencySimApplication {

    public static void main(String[] args) {
        SpringApplication.run(Pf2CurrencySimApplication.class, args);
    }

}
