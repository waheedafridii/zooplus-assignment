package com.crypto.currencyconverter.service;

import com.crypto.currencyconverter.dto.IPLocationDto;
import com.crypto.currencyconverter.exception.InvalidIpException;
import com.crypto.currencyconverter.gateway.IPLocationGateway;
import com.google.common.net.InetAddresses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IPService {

    private final IPLocationGateway ipLocationGateway;

    @Autowired
    public IPService(IPLocationGateway ipLocationGateway) {
        this.ipLocationGateway = ipLocationGateway;
    }

    public IPLocationDto getIPLocationDto(String clientIP,String clientOverrideIP){
        String ip = clientOverrideIP.isEmpty() ? clientIP : clientOverrideIP;
        guardAgainstInvalidIp(ip);

        return ipLocationGateway.fetchLocationFromIP(ip);
    }

    private void guardAgainstInvalidIp(String ip) {
        if (!InetAddresses.isInetAddress(ip)) {
            throw new InvalidIpException(ip);
        }
    }
}
