package com.crypto.currencyconverter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CryptCurrencyListDto {

    private String name;
    private String symbol;
}
