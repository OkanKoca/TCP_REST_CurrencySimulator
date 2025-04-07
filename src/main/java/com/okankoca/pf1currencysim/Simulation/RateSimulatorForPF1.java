package com.okankoca.pf1currencysim.Simulation;

import com.okankoca.pf2currencysim.model.Rate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class RateSimulatorForPF1 {
    RatesInitialization ratesInitialization; //= new RatesInitialization();

    public RateSimulatorForPF1(RatesInitialization ratesInitialization) {
        this.ratesInitialization = ratesInitialization;
    }

    public void simulateRates() { // verileri simüle eder ve ratesinitializationdaki liste şeklinde tutulan kurlar güncellenir.
        //System.out.println("Simulating rates");
        Random random = new Random();
        List<Rate> rates = ratesInitialization.getAllRates();
        rates.forEach(rate -> {
            double randomRate = random.nextDouble();
            double bidChange = ((randomRate - 0.5) * rate.getBid()) * 0.01;
            double askChange = ((randomRate - 0.5) * rate.getAsk()) * 0.01;

            rate.setBid(rate.getBid() + bidChange);
            //System.out.println("updated bid of " + rate.getRateName() + " " + rate.getBid());

            rate.setAsk(rate.getAsk() + askChange);
            //System.out.println("updated ask of " + rate.getRateName() + " " + rate.getAsk());
            //System.out.println();

            rate.setTimestamp(LocalDateTime.now());
        });
        ratesInitialization.updateRates(rates);
    }
}
