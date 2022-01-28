package com.crypto.currencyconverter.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinIOAssetsDto {

    @JsonProperty(value="asset_id")
    private String assetId;
    private String name;
    @JsonProperty(value = "type_is_crypto")
    private int typeIsCrypto;
}
