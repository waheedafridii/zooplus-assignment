package com.crypto.currencyconverter.gateway;

import com.crypto.currencyconverter.dto.CoinIOAssetsDto;
import com.crypto.currencyconverter.dto.ExchangeRateDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class CoinIOGateway {

    private final OkHttpClient client;
    private final String BASE_URL;

    @Autowired
    public CoinIOGateway(@Value("${crypto.coinioapi}") String base_url) {
        this.BASE_URL = base_url;
        this.client = new OkHttpClient();
    }

    public List<CoinIOAssetsDto> fetchAllCurrencies() {
        Request request = new Request.Builder()
                .url(BASE_URL + "assets")
                .header("X-COINAPI-Key", "A2CB342F-1F2A-4100-A2FD-C6A1335B7A67")
                .build();

        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            String responseBody = response.body().string();
            return new ObjectMapper().readValue(responseBody, new TypeReference<>(){});
        } catch (IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        return null;
    }

    public ExchangeRateDto getExchangeRate(String assetId, String currency) {
        Request request = new Request.Builder()
                .url(BASE_URL + "exchangeRate/" + assetId + "/" + currency)
                .header("X-COINAPI-Key", "A2CB342F-1F2A-4100-A2FD-C6A1335B7A67")
                .build();

        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            String responseBody = response.body().string();
            return new ObjectMapper().readValue(responseBody, new TypeReference<>() {});
        } catch (IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        return null;
    }
}
