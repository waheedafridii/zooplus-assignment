package com.crypto.currencyconverter.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Currency;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRateDto {

    private String rate;
    @JsonProperty(value = "asset_id_quote")
    private String currencySymbol;

    public void setRate(Double rate) {
        this.rate = String.format("%.2f",rate);
    }

    public void setCurrency(String currencyCode) {
        Currency currency = Currency.getInstance(currencyCode);
        this.currencySymbol = currency.getSymbol();
    }
}
