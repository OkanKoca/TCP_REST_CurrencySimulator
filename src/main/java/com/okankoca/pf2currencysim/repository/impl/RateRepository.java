package com.okankoca.pf2currencysim.repository.impl;

import com.okankoca.pf2currencysim.config.ConfigReader;
import com.okankoca.pf2currencysim.model.Rate;
import com.okankoca.pf2currencysim.repository.IRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RateRepository implements IRateRepository {
    private List<Rate> rates;
    @Autowired
    ConfigReader configReader;

    @Override
    public List<Rate> getAllRates() {
        return rates;
    }

    @Override
    public Rate getRateByRateName(String rateName) {
        for(Rate rate : rates) {
            if(rate.getRateName().equalsIgnoreCase(rateName)) {
                return rate;
            }
        }
        System.out.println("rate could not be found");
        return null;
    }

    @Override
    public void createRate() {
         rates = configReader.readConfig();
         for(Rate r : rates) {
             System.out.println("repository : " + r.getRateName());
             System.out.println("repository : " + r.getBid());
             System.out.println("repository : " + r.getAsk());
             System.out.println("repository : " + r.getTimestamp());
         }
    }


}
