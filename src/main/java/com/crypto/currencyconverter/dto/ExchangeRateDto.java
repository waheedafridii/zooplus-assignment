package com.crypto.currencyconverter.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ExchangeRateDto {

    private String rate;
    private String currencySymbol;
}
