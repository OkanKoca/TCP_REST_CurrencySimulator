package com.okankoca.pf1currencysim.Config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.okankoca.pf2currencysim.model.Rate;
import org.springframework.core.io.ClassPathResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class JsonReader {
    public static List<Rate> getRates() {
        try{
            System.out.println("Reading config file");
            InputStream inputStream = new ClassPathResource("json/ratesForPF1.json").getInputStream();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.findAndRegisterModules();
            List<Rate> rates = objectMapper.readValue(inputStream, new TypeReference<>(){});
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
