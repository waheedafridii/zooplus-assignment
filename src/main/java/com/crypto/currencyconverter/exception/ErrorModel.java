package com.crypto.currencyconverter.exception;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@Data
@ToString
public class ErrorModel {

    private String message;
    private String type;
}
