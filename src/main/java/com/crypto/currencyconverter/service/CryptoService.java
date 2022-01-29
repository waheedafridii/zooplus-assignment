package com.crypto.currencyconverter.service;

import com.crypto.currencyconverter.dto.CoinIOAssetsDto;
import com.crypto.currencyconverter.dto.ExchangeRateDto;
import com.crypto.currencyconverter.dto.IPLocationDto;
import com.crypto.currencyconverter.exception.InvalidIpException;
import com.crypto.currencyconverter.gateway.CoinIOGateway;
import com.crypto.currencyconverter.gateway.IPLocationGateway;
import com.google.common.net.InetAddresses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CryptoService {

    private final CoinIOGateway coinIOGateway;
    private final IPService ipService;

    @Autowired
    public CryptoService(CoinIOGateway coinIOGateway, IPService ipService) {
        this.coinIOGateway = coinIOGateway;
        this.ipService = ipService;
    }

    public List<CoinIOAssetsDto> listCryptoCurrencies() {
        return coinIOGateway.fetchAllCurrencies()
                .stream().filter(CoinIOAssetsDto::isCrypto)
                .collect(Collectors.toList());
    }

    public ExchangeRateDto getExchangeRate(String assetId,String clientIP, String clientIPOverride) {

        IPLocationDto ipLocationDto = ipService.getIPLocationDto(clientIP,clientIPOverride);
        ExchangeRateDto exchangeRateDto = coinIOGateway.getExchangeRate(assetId,ipLocationDto.getCurrency());
        exchangeRateDto.setCurrency(ipLocationDto.getCurrency());
        return exchangeRateDto;
    }
}
