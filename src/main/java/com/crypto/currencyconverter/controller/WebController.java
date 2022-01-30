package com.crypto.currencyconverter.controller;

import com.crypto.currencyconverter.service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class WebController {

    private final CryptoService cryptoService;

    @Autowired
    public WebController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("cryptocurrencyList",cryptoService.listCryptoCurrencies());
        return "home";
    }
}
