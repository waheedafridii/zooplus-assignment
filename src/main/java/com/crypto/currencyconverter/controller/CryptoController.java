package com.crypto.currencyconverter.controller;

import com.crypto.currencyconverter.dto.CoinIOAssetsDto;
import com.crypto.currencyconverter.dto.ExchangeRateDto;
import com.crypto.currencyconverter.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
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
    public List<CoinIOAssetsDto> listCryptoCurrencies() {
        return cryptoService.listCryptoCurrencies();
    }

    @GetMapping(value = "exchange/rate/{assetId}")
    public ExchangeRateDto getExchangeRate(HttpServletRequest request, @PathVariable String assetId, @RequestParam(defaultValue = "") String ipAddress) {
        String clientIP = request.getRemoteAddr();
        return cryptoService.getExchangeRate(assetId,clientIP,ipAddress);
    }
}
