package com.crypto.currencyconverter.exception;

public abstract class CryptoException extends  RuntimeException{

    public CryptoException(String message) {
        super(message);
    }
}
