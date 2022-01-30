package com.crypto.currencyconverter.gateway;

import com.crypto.currencyconverter.dto.CoinIOAssetsDto;
import com.crypto.currencyconverter.dto.ExchangeRateDto;
import com.crypto.currencyconverter.exception.ExternalCallFailedException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CoinIOGatewayTest {

    public static MockWebServer mockBackEnd;

    private CoinIOGateway coinIOGateway;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();

    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @BeforeEach
    void initialize(){
        String baseUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
        this.coinIOGateway = new CoinIOGateway(baseUrl,"ABCSC-EDAED");
    }

    @Test
    void shouldFetchAllCurrencies(){
        String resp = "[{\"asset_id\":\"USD\",\"name\":\"US Dollar\",\"type_is_crypto\":0,\"data_quote_start\":\"2014-02-24T17:43:05.0000000Z\",\"data_quote_end\":\"2022-01-29T17:21:47.7550205Z\",\"data_orderbook_start\":\"2014-02-24T17:43:05.0000000Z\",\"data_orderbook_end\":\"2020-08-05T14:38:00.7082850Z\",\"data_trade_start\":\"2010-07-17T23:09:17.0000000Z\",\"data_trade_end\":\"2022-01-29T17:21:25.1324880Z\",\"data_symbols_count\":96694,\"volume_1hrs_usd\":851118943861.4,\"volume_1day_usd\":24303712928336.91,\"volume_1mth_usd\":1137465821316998.5,\"id_icon\":\"0a4185f2-1a03-4a7c-b866-ba7076d8c73b\",\"data_start\":\"2010-07-17\",\"data_end\":\"2022-01-29\"},{\"asset_id\":\"BTC\",\"name\":\"Bitcoin\",\"type_is_crypto\":1,\"data_quote_start\":\"2014-02-24T17:43:05.0000000Z\",\"data_quote_end\":\"2022-01-29T17:21:47.5116401Z\",\"data_orderbook_start\":\"2014-02-24T17:43:05.0000000Z\",\"data_orderbook_end\":\"2020-08-05T14:38:38.3413202Z\",\"data_trade_start\":\"2010-07-17T23:09:17.0000000Z\",\"data_trade_end\":\"2022-01-29T17:21:24.3530000Z\",\"data_symbols_count\":75956,\"volume_1hrs_usd\":1676183792811.73,\"volume_1day_usd\":51403785151417.33,\"volume_1mth_usd\":5215188659326169,\"price_usd\":37618.35345193613,\"id_icon\":\"4caf2b16-a017-4e26-a348-2cea69c34cba\",\"data_start\":\"2010-07-17\",\"data_end\":\"2022-01-29\"}]";
        mockBackEnd.enqueue(new MockResponse().
                setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .setBody(resp)
        );

        List<CoinIOAssetsDto> rspFromClient = coinIOGateway.fetchAllCurrencies();

        assertThat(rspFromClient.size()).isEqualTo(2);
    }

    @Test
    void shouldFetchExchangeRate(){
        //given
        String resp = "{\"time\":\"2022-01-29T18:41:30.9000000Z\",\"asset_id_base\":\"BTC\",\"asset_id_quote\":\"USD\",\"rate\":37602.30877254769,\"src_side_base\":[{\"time\":\"2022-01-29T18:41:30.9000000Z\",\"asset\":\"SUSHI\",\"rate\":37643.60242028096,\"volume\":2045103.0604904976},{\"time\":\"2022-01-29T18:41:30.9000000Z\",\"asset\":\"LINK\",\"rate\":37617.092020134165,\"volume\":17807112.283712715},{\"time\":\"2022-01-29T18:41:30.9000000Z\",\"asset\":\"GBP\",\"rate\":37597.32637025249,\"volume\":34277698.381099984},{\"time\":\"2022-01-29T18:41:30.9000000Z\",\"asset\":\"HBAR\",\"rate\":37634.43596268024,\"volume\":2320391.20662},{\"time\":\"2022-01-29T18:41:30.9000000Z\",\"asset\":\"ETH\",\"rate\":37606.00506935375,\"volume\":191763993.50966576},{\"time\":\"2022-01-29T18:41:30.9000000Z\",\"asset\":\"MANA\",\"rate\":37609.25477033801,\"volume\":8652518.7832404},{\"time\":\"2022-01-29T18:41:30.9000000Z\",\"asset\":\"QTUM\",\"rate\":37627.94853489544,\"volume\":177085.22766559882},{\"time\":\"2022-01-29T18:41:30.9000000Z\",\"asset\":\"CHZ\",\"rate\":37610.17884219124,\"volume\":1199316.0438399473},{\"time\":\"2022-01-29T18:41:30.9000000Z\",\"asset\":\"USD\",\"rate\":37601.23123226517,\"volume\":793025699.2835159},{\"time\":\"2022-01-29T18:41:30.9000000Z\",\"asset\":\"DAI\",\"rate\":37590.908141744745,\"volume\":2478629.4460418257},{\"time\":\"2022-01-29T18:41:30.9000000Z\",\"asset\":\"DOGE\",\"rate\":37560.497114561316,\"volume\":5459648.136599387},{\"time\":\"2022-01-29T18:41:30.9000000Z\",\"asset\":\"MATIC\",\"rate\":37593.178208780715,\"volume\":33334752.91107642},{\"time\":\"2022-01-29T18:41:30.9000000Z\",\"asset\":\"AVAX\",\"rate\":37613.06164209487,\"volume\":12724019.643612403},{\"time\":\"2022-01-29T18:41:30.9000000Z\",\"asset\":\"ANT\",\"rate\":37609.39705685648,\"volume\":2299204.334337262},{\"time\":\"2022-01-29T18:41:30.9000000Z\",\"asset\":\"XTZ\",\"rate\":37623.565164201726,\"volume\":1808481.4252058524},{\"time\":\"2022-01-29T18:41:30.9000000Z\",\"asset\":\"UNI\",\"rate\":37630.575454722755,\"volume\":4387229.575314244},{\"time\":\"2022-01-29T18:41:30.9000000Z\",\"asset\":\"REP\",\"rate\":37575.02054843292,\"volume\":58399.94438720658},{\"time\":\"2022-01-29T18:41:30.9000000Z\",\"asset\":\"XLM\",\"rate\":37630.93673983998,\"volume\":3046342.6273305616},{\"time\":\"2022-01-29T18:41:30.9000000Z\",\"asset\":\"YFI\",\"rate\":37589.50236712236,\"volume\":1202599.08311575},{\"time\":\"2022-01-29T18:41:30.9000000Z\",\"asset\":\"OCEAN\",\"rate\":37588.96762798807,\"volume\":354202.08597007394}]}";
        String assetID = "BTC";
        String currency = "USD";
        mockBackEnd.enqueue(new MockResponse().
                setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .setBody(resp)
        );

        //when
        ExchangeRateDto rspFromClient = coinIOGateway.getExchangeRate(assetID,currency);

        //Then
        assertThat(rspFromClient.getRate()).isEqualTo("37602.31");
    }

    @Test
    void shouldReturnExceptionAgainstInvalidIP(){
        //given
        String resp = "{\"status\":\"fail\"}";
        mockBackEnd.enqueue(new MockResponse().
                setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .setBody(resp)
        );

        //Then
        assertThrows(ExternalCallFailedException.class,()-> coinIOGateway.fetchAllCurrencies());
    }

    @Test
    void shouldReturnExceptionOnFetchCurrenciesAPIWhenInvalidJsonReturn(){
        //given
        String resp = "\"{\\\"statasdsadasus\\\":\\\"fail\\\"}\"";
        mockBackEnd.enqueue(new MockResponse().
                setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .setBody(resp)
        );

        //Then
        assertThrows(ExternalCallFailedException.class,()-> coinIOGateway.fetchAllCurrencies());
    }

    @Test
    void shouldReturnExceptionOnExchangeRateAPIWhenInvalidJsonReturn(){
        //given
        String resp = "\"{\\\"statasdsadasus\\\":\\\"fail\\\"}\"";
        String assetID = "BTC";
        String currency = "USD";
        mockBackEnd.enqueue(new MockResponse().
                setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .setBody(resp)
        );

        //Then
        assertThrows(ExternalCallFailedException.class,()-> coinIOGateway.getExchangeRate(assetID,currency));
    }
}
