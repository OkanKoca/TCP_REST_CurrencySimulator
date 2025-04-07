package com.okankoca.pf2currencysim.service.impl;

import com.okankoca.pf2currencysim.model.Rate;
import com.okankoca.pf2currencysim.repository.IRateRepository;
import com.okankoca.pf2currencysim.service.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RateServiceImpl implements IRateService {
    @Autowired
    private IRateRepository rateRepository;

    @Override
    public List<Rate> getAllRates() {
        return rateRepository.getAllRates();
    }

    @Override
    public Rate getRateByRateName(String rateName) {
        return rateRepository.getRateByRateName(rateName);
    }

    @Override
    public void createRate() {
        rateRepository.createRate();
    }

}
