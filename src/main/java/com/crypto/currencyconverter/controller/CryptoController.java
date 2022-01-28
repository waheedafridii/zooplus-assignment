package com.crypto.currencyconverter.controller;

import com.crypto.currencyconverter.dto.CryptCurrencyListDto;
import com.crypto.currencyconverter.dto.ExchangeRateDto;
import com.crypto.currencyconverter.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("crypto")
public class CryptoController {

    private final CryptoService cryptoService;

    @Autowired
    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @GetMapping(value = "list")
    public List<CryptCurrencyListDto> listCryptoCurrencies(){
        return cryptoService.listCryptoCurrencies();
    }

    @GetMapping(value = "exchange/rate")
    public ExchangeRateDto getExchangeRate(){
        return cryptoService.getExchangeRate();
    }
}
