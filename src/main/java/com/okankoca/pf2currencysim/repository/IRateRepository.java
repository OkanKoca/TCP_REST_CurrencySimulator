package com.okankoca.pf2currencysim.repository;

import com.okankoca.pf2currencysim.model.Rate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IRateRepository {
    List<Rate> getAllRates();
    Rate getRateByRateName(String rateName);
    void createRate();
}
