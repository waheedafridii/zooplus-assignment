package com.crypto.currencyconverter.gateway;

import com.crypto.currencyconverter.dto.IPLocationDto;
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
import java.util.Objects;

@Service
public class IPLocationGateway {

    private final OkHttpClient client;
    private final String BASE_URL;

    @Autowired
    public IPLocationGateway(@Value("${iplocation.api}") String ipLocationApi) {
        this.BASE_URL = ipLocationApi;
        this.client = new OkHttpClient();
    }

    public IPLocationDto fetchLocationFromIP(String ipAddress){
        URL url = new HttpUrl.Builder()
                .scheme("http")
                .host(BASE_URL)
                .addPathSegment("json")
                .addPathSegment(ipAddress)
                .addQueryParameter("fields","status,currency")
                .build().url();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);

        try {
            Response response = call.execute();
            String responseBody = Objects.requireNonNull(response.body()).string();
            return new ObjectMapper().readValue(responseBody, new TypeReference<IPLocationDto>(){});
        }
        catch (IOException e){
            // TODO: need to handle proper Exception
            System.out.println("Stack Trace Message "+ Arrays.toString(e.getStackTrace()));
        }
        return null;
    }
}
