package com.crypto.currencyconverter.service;

import com.crypto.currencyconverter.dto.CoinIOAssetsDto;
import com.crypto.currencyconverter.dto.ExchangeRateDto;
import com.crypto.currencyconverter.gateway.CoinIOGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CryptoService {

    private final CoinIOGateway coinIOGateway;

    @Autowired
    public CryptoService(CoinIOGateway coinIOGateway) {
        this.coinIOGateway = coinIOGateway;
    }

    public List<CoinIOAssetsDto> listCryptoCurrencies() {
        // TODO: Business logic need to be done
        return coinIOGateway.fetchAllCurrencies();
    }

    public ExchangeRateDto getExchangeRate(String assetId) {
        // TODO: Business logic need to be done
        return coinIOGateway.getExchangeRate(assetId,"USD");
    }
}
