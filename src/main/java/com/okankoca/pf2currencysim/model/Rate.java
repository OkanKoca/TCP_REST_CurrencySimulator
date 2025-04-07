package com.okankoca.pf2currencysim.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rate {
    private int id;
    private String rateName;
    private double bid;
    private double ask;
    private LocalDateTime timestamp;
}
