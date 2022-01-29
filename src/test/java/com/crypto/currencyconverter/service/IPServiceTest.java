package com.crypto.currencyconverter.service;

import com.crypto.currencyconverter.dto.IPLocationDto;
import com.crypto.currencyconverter.exception.InvalidIpException;
import com.crypto.currencyconverter.gateway.IPLocationGateway;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class IPServiceTest {

    private IPService ipService;

    @Mock
    private IPLocationGateway ipLocationGateway;

    @BeforeEach
    void setup(){
        this.ipService = new IPService(ipLocationGateway);
    }

    @Test
    void shouldUseClientIPWhenOverrideIPIsEmpty(){
        //given
        String clientIP = "45.43.71.133";
        IPLocationDto expectedResp = new IPLocationDto("success","USD");
        given(ipLocationGateway.fetchLocationFromIP(clientIP)).willReturn(expectedResp);

        //when
        IPLocationDto actualResp = ipService.getIPLocationDto(clientIP, Strings.EMPTY);

        //Then
        assertThat(actualResp).isEqualTo(expectedResp);
    }

    @Test
    void shouldUseClientIPWhenClientIPAndOverrideIPisPresent(){
        //given
        String clientIP = "45.43.71.133";
        String clientOverrideIP = "69.162.81.155";
        IPLocationDto expectedResp = new IPLocationDto("success","USD");
        given(ipLocationGateway.fetchLocationFromIP(clientOverrideIP)).willReturn(expectedResp);

        //when
        IPLocationDto actualResp = ipService.getIPLocationDto(clientIP,clientOverrideIP);

        //Then
        assertThat(actualResp).isEqualTo(expectedResp);
        verify(ipLocationGateway,times(0)).fetchLocationFromIP(clientIP);
    }

    @Test
    void shouldReturnExceptionWhenIPisInvalid(){
        //given
        String clientIP = "45.43.71.133";
        String clientOverrideIP = "69.162.81.15512313";

        //Then
        assertThrows(InvalidIpException.class,()->ipService.getIPLocationDto(clientIP,clientOverrideIP));
        verify(ipLocationGateway,times(0)).fetchLocationFromIP(any());
    }
}
