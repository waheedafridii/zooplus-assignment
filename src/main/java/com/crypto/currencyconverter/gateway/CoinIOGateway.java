package com.crypto.currencyconverter.gateway;

import com.crypto.currencyconverter.dto.CoinIOAssetsDto;
import com.crypto.currencyconverter.dto.ExchangeRateDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class CoinIOGateway {

    private final OkHttpClient client;
    private final String host;
    private final String COIN_API_KEY;
    private final String COIN_API_HEADER = "X-COINAPI-KEY";

    @Autowired
    public CoinIOGateway(@Value("${crypto.coinioapi}") String coinIOApi,@Value("${crypto.apiKey}") String coinApikey) {
        this.COIN_API_KEY = coinApikey;
        this.host = coinIOApi;
        this.client = new OkHttpClient();
    }

    public List<CoinIOAssetsDto> fetchAllCurrencies() {
        URL url = new HttpUrl.Builder()
                .scheme("https")
                .host(host)
                .addPathSegment("v1")
                .addPathSegment("assets")
                .build().url();

        Request request = new Request.Builder()
                .url(url)
                .header(COIN_API_HEADER,COIN_API_KEY)
                .build();

        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            String responseBody = Objects.requireNonNull(response.body()).string();
            return new ObjectMapper().readValue(responseBody, new TypeReference<>(){});
        } catch (IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        return null;
    }

    public ExchangeRateDto getExchangeRate(String assetId, String currency) {
        URL url = new HttpUrl.Builder()
                .scheme("https")
                .host(host)
                .addPathSegment("v1")
                .addPathSegment("exchangerate")
                .addPathSegment(assetId)
                .addPathSegment(currency)
                .build().url();

        Request request = new Request.Builder()
                .url(url)
                .header(COIN_API_HEADER,COIN_API_KEY)
                .build();

        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            String responseBody = Objects.requireNonNull(response.body()).string();
            return new ObjectMapper().readValue(responseBody, new TypeReference<>() {});
        } catch (IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            // TODO: need to handle proper Exception
        }
        return null;
    }
}
