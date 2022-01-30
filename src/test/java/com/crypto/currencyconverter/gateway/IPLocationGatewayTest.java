package com.crypto.currencyconverter.gateway;

import com.crypto.currencyconverter.dto.IPLocationDto;
import com.crypto.currencyconverter.exception.ExternalCallFailedException;
import com.crypto.currencyconverter.exception.InvalidIpException;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class IPLocationGatewayTest {

    public static MockWebServer mockBackEnd;

    private IPLocationGateway ipLocationGateway;

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
        this.ipLocationGateway = new IPLocationGateway(baseUrl);
    }

    @Test
    void shouldReturnCurrencyWhenFetchLocationFromRegion(){
        //given
        String resp = "{\"status\":\"success\",\"currency\":\"CAD\"}";
        String ipAddress = "10.0.0.0";
        mockBackEnd.enqueue(new MockResponse().
                setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .setBody(resp));
        //when
        IPLocationDto rspFromClient = ipLocationGateway.fetchLocationFromIP(ipAddress);

        //then
        assertThat(rspFromClient.getStatus()).isEqualTo("success");
        assertThat(rspFromClient.getCurrency()).isEqualTo("CAD");
    }

    @Test
    void shouldReturnExceptionAgainInvalidIP(){
        //given
        String resp = "{\"status\":\"fail\"}";
        String ipAddress = "10.0.02313123.0";
        mockBackEnd.enqueue(new MockResponse().
                setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .setBody(resp));

        //Then
        assertThrows(ExternalCallFailedException.class,() -> ipLocationGateway.fetchLocationFromIP(ipAddress));
    }

    @Test
    void shouldReturnExceptionWhenInvalidJsonReturn(){
        //given
        String resp = "{\\\"status\\\":\\\"fail\\\"}\"";
        String ipAddress = "10.0.02313123.0";
        mockBackEnd.enqueue(new MockResponse().
                setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .setBody(resp));

        //Then
        assertThrows(ExternalCallFailedException.class,() -> ipLocationGateway.fetchLocationFromIP(ipAddress));
    }

}
