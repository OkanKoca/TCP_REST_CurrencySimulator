package com.okankoca.pf2currencysim.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.okankoca.pf2currencysim.model.Rate;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

@Configuration
public class ConfigReader {

    @Autowired
    private ObjectMapper objectMapper;

    //@Bean
    @PostConstruct
    public List<Rate> readConfig() {
        try{
            System.out.println("Reading config file");
            InputStream inputStream = new ClassPathResource("json/ratesForPF2.json").getInputStream();
            List<Rate> rates =  objectMapper.readValue(inputStream, new TypeReference<>(){});
            for(Rate r : rates){
                System.out.println(r.getId());
                System.out.println(r.getRateName());
                System.out.println(r.getBid());
                System.out.println(r.getAsk());
                System.out.println(r.getTimestamp());
            }
            return rates;
        }
        catch (Exception e){
            System.err.println("A problem occurred while loading first rates from JSON file: " + e.getMessage());
        }
        return Collections.emptyList();
    }
}
