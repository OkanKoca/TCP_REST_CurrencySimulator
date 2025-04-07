package com.okankoca.pf2currencysim.controller;

import com.okankoca.pf2currencysim.model.Rate;
import com.okankoca.pf2currencysim.service.IRateService;

import java.util.List;

public interface IRateController {
    List<Rate> getAllRates();
    Rate getRateByRateName(String rateName);
}
