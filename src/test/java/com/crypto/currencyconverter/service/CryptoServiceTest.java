package com.crypto.currencyconverter.service;

import com.crypto.currencyconverter.dto.CoinIOAssetsDto;
import com.crypto.currencyconverter.dto.ExchangeRateDto;
import com.crypto.currencyconverter.dto.IPLocationDto;
import com.crypto.currencyconverter.gateway.CoinIOGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class CryptoServiceTest {

    private CryptoService cryptoService;

    @Mock
    private CoinIOGateway coinIOGateway;

    @Mock
    private IPService ipService;

    @BeforeEach
    void setup(){
        this.cryptoService = new CryptoService(coinIOGateway,ipService);
    }

    @Test
    void shouldOnlyListCurrenciesWithCrypto(){
        //given
        List<CoinIOAssetsDto> coinIOAssetsDtoList = Arrays.asList(
                new CoinIOAssetsDto("BTC","Bitcoin",1),
                new CoinIOAssetsDto("USD","Dollar",0)
        );
        given(coinIOGateway.fetchAllCurrencies()).willReturn(coinIOAssetsDtoList);

        //when
        List<CoinIOAssetsDto> actualResponse =  cryptoService.listCryptoCurrencies();

        //then
        assertThat(actualResponse.size()).isEqualTo(1);
        assertThat(actualResponse.get(0).isCrypto()).isTrue();

    }

    @Test
    void shouldReturnEmptyListIfNoCurrenciesIsFound(){
        //given
        List<CoinIOAssetsDto> coinIOAssetsDtoList = Arrays.asList(
                new CoinIOAssetsDto("test","testName",0),
                new CoinIOAssetsDto("test","testName",0)
        );
        //when
        List<CoinIOAssetsDto> actualResponse =  cryptoService.listCryptoCurrencies();

        //then
        assertThat(actualResponse.size()).isZero();
    }

    @Test
    void shouldReturnExchangeRateAgainstRegion(){
        //given
        String clientIP = "69.162.81.155";
        String clientOverrideIP = "69.162.81.155";
        String assetId = "BTC";
        ExchangeRateDto expectedResponse = new ExchangeRateDto("123.22","USD");
        IPLocationDto ipLocationDto = new IPLocationDto("Success","USD");
        given(ipService.getIPLocationDto(clientIP,clientOverrideIP)).willReturn(ipLocationDto);
        given(coinIOGateway.getExchangeRate(assetId,ipLocationDto.getCurrency())).willReturn(expectedResponse);

        //when
        ExchangeRateDto actualResponse = cryptoService.getExchangeRate(assetId,clientIP,clientOverrideIP);

        //then
        assertThat(actualResponse).isEqualTo(expectedResponse);
        assertThat(actualResponse.getCurrencySymbol()).isEqualTo(expectedResponse.getCurrencySymbol());
    }

    @Test
    void shouldReturnExchangeRateInTwoDecimalPlaces(){
        //given
        ExchangeRateDto exchangeRateDto = new ExchangeRateDto();
        exchangeRateDto.setRate(123.22);
        exchangeRateDto.setCurrency("USD");

        assertThat(exchangeRateDto.getRate()).isEqualTo("123.22");
    }
}
