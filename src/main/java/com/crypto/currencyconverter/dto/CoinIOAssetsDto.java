package com.crypto.currencyconverter.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinIOAssetsDto {

    private String asset_id;
    private String name;
    private int type_is_crypto;
}
