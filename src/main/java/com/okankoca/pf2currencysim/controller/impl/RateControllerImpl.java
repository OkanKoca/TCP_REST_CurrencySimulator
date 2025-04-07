package com.okankoca.pf2currencysim.controller.impl;

import com.okankoca.pf2currencysim.controller.IRateController;
import com.okankoca.pf2currencysim.model.Rate;
import com.okankoca.pf2currencysim.service.IRateService;
import com.okankoca.pf2currencysim.service.impl.RateServiceImpl;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rates")
public class RateControllerImpl implements IRateController {

    private final IRateService rateService;

    public RateControllerImpl(IRateService rateService) {
        this.rateService = rateService;
        rateService.createRate();
    }

    @GetMapping()
    @Override
    public List<Rate> getAllRates() {
        return rateService.getAllRates();
    }

    @GetMapping("/{rateName}")
    @Override
    public Rate getRateByRateName(@PathVariable String rateName) {
        return rateService.getRateByRateName(rateName);
    }

}
