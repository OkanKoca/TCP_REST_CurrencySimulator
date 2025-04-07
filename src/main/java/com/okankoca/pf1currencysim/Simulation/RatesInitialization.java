package com.okankoca.pf1currencysim.Simulation;

import com.okankoca.pf1currencysim.Config.JsonReader;
import com.okankoca.pf2currencysim.model.Rate;

import java.time.LocalDateTime;
import java.util.List;

public class RatesInitialization {
    private List<Rate> rates;

    public RatesInitialization() {
        rates = JsonReader.getRates();
    }

    public List<Rate> getAllRates() {
        return rates;
    }

    public void updateRates(List<Rate> updatedRates){
        for(Rate updatedRate : updatedRates){
            for(Rate rate : rates){
                if (rate.getRateName().equalsIgnoreCase(updatedRate.getRateName())){
                    rate.setBid(updatedRate.getBid());
                    rate.setAsk(updatedRate.getAsk());
                    rate.setTimestamp(LocalDateTime.now());
                }
            }
        }
    }



}
