package com.idrust.bmfpriceapi.services;

public interface MoneyConverterService {

    /**
     * Converte um valor em dólares (USD) para reais (R$)
     *
     * @param usdValue  O valor em dólares a ser convertido
     * @return O valor em reais
     */
    Float convertToReal(Float usdValue);

}
