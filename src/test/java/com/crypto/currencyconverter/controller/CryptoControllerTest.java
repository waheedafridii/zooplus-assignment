package com.crypto.currencyconverter.controller;

import com.crypto.currencyconverter.dto.CoinIOAssetsDto;
import com.crypto.currencyconverter.dto.ExchangeRateDto;
import com.crypto.currencyconverter.service.CryptoService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(CryptoController.class)
class CryptoControllerTest {

    @MockBean
    private CryptoService cryptoService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnAllCurrencies() throws Exception{
        //given
        when(cryptoService.listCryptoCurrencies()).thenReturn(getCoinAssetDtoList());

        //when
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/crypto/list").contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void shouldReturnExchangeRate() throws Exception{
        //given
        when(cryptoService.getExchangeRate(any(),any(),any())).thenReturn(getExchangeDto());

        //when
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/crypto/exchange/rate/BTC").contentType(MediaType.APPLICATION_JSON)).andReturn();

        //Then
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    private List<CoinIOAssetsDto> getCoinAssetDtoList() {
        return Arrays.asList(
                new CoinIOAssetsDto("test","testName",1),
                new CoinIOAssetsDto("test","testName",1),
                new CoinIOAssetsDto("test","testName",1)
        );
    }

    private ExchangeRateDto getExchangeDto(){
        ExchangeRateDto dto = new ExchangeRateDto();
        dto.setCurrency("USD");
        dto.setRate(122.33);
        return dto;
    }
}
