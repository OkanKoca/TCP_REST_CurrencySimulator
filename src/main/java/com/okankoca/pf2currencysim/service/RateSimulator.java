package com.okankoca.pf2currencysim.service;

import com.okankoca.pf2currencysim.repository.impl.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.okankoca.pf2currencysim.model.Rate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class RateSimulator {

    //
    // kurlar için farklı algoritmalar kullanılabilir fakat şimdilik bu kalsın
    //

    private final IRateService rateService;

    public RateSimulator(IRateService rateService) {
        this.rateService = rateService;
    }

    @Scheduled(fixedRate = 5000)
    public void simulateRates() {
        System.out.println("Simulating rates");
        Random random = new Random();
        rateService.getAllRates().forEach(rate -> {
            double randomRate = random.nextDouble();
            double bidChange = (( randomRate-0.5) * rate.getBid())* 0.01;
            double askChange = ((randomRate -0.5) * rate.getAsk())* 0.01;

            rate.setBid(rate.getBid() + bidChange);
            System.out.println("updated bid of " + rate.getRateName() + " "+ rate.getBid());

            rate.setAsk(rate.getAsk() + askChange);
            System.out.println("updated ask of " + rate.getRateName() + " "+ rate.getAsk());
            System.out.println();

            rate.setTimestamp(LocalDateTime.now());
        });
    }
}
