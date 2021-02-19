package com.idrust.bmfpriceapi.exceptions;

public class CropPriceCalculationException extends Exception {

    public CropPriceCalculationException(String message) {
        super(message);
    }

    public CropPriceCalculationException(String message, Throwable cause) {
        super(message, cause);
    }
}
