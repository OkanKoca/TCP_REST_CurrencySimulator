package com.okankoca.pf2currencysim.service;

import com.okankoca.pf2currencysim.model.Rate;

import java.util.List;

public interface IRateService {
    List<Rate> getAllRates();
    Rate getRateByRateName(String rateName);
    void createRate();
}
