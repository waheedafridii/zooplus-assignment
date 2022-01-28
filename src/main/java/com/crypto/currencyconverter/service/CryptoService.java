package com.crypto.currencyconverter.service;

import com.crypto.currencyconverter.dto.CryptCurrencyListDto;
import com.crypto.currencyconverter.dto.ExchangeRateDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CryptoService {

    public List<CryptCurrencyListDto> listCryptoCurrencies(){
        // TODO: Business logic need to be done
        List<CryptCurrencyListDto> cryptoCurrencies = new ArrayList<>();
        cryptoCurrencies.add(new CryptCurrencyListDto("BitCoin","BTC"));
        cryptoCurrencies.add(new CryptCurrencyListDto("Ripple","XRP"));
        cryptoCurrencies.add(new CryptCurrencyListDto("LiteCoin","LTC"));
        cryptoCurrencies.add(new CryptCurrencyListDto("NameCoin","NMC"));

        return cryptoCurrencies;
    }

    public ExchangeRateDto getExchangeRate(){
        // TODO: Business logic need to be done
        ExchangeRateDto exchangeRateDto = new ExchangeRateDto();
        exchangeRateDto.setRate("1234.56");
        exchangeRateDto.setCurrencySymbol("$");
        return exchangeRateDto;
    }
}
