package com.idrust.bmfpriceapi.exceptions;

public class EconomiaAPIException extends Exception {

    public EconomiaAPIException(String message) {
        super(message);
    }

    public EconomiaAPIException(String message, Throwable cause) {
        super(message, cause);
    }
}
