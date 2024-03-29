package com.crypto.currencyconverter.gateway;

import com.crypto.currencyconverter.dto.IPLocationDto;
import com.crypto.currencyconverter.exception.ExternalCallFailedException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

@Slf4j
@Service
public class IPLocationGateway {

    private final OkHttpClient client;
    private final HttpUrl baseURL;

    @Autowired
    public IPLocationGateway(@Value("${iplocation.api}") String ipLocationApi) {
        this.baseURL = HttpUrl.parse(ipLocationApi);
        this.client = new OkHttpClient();
    }

    public IPLocationDto fetchLocationFromIP(String ipAddress){
        URL url = new HttpUrl.Builder()
                .scheme(baseURL.scheme())
                .host(baseURL.host())
                .port(baseURL.port())
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
            IPLocationDto ipLocationDto = new ObjectMapper().readValue(responseBody, new TypeReference<>(){});
            guardAgainstStatus(ipLocationDto);
            return ipLocationDto;
        }
        catch (IOException e){
            log.error("fetch Location Exception ",e);
            throw new ExternalCallFailedException(Strings.EMPTY);
        }
    }

    private void guardAgainstStatus(IPLocationDto ipLocationDto) {
        if ("fail".equals(ipLocationDto.getStatus())) {
            throw new ExternalCallFailedException("No location could be mapped for given IP");
        }
    }
}
