package com.idrust.bmfpriceapi.services;

import com.idrust.bmfpriceapi.exceptions.EconomiaAPIException;

public interface EconomiaService {

    /**
     * Requisita a API de economia para buscar o valor do dólar no atual momento
     *
     * @return O valor do dólar em reais no atual momento
     * @throws EconomiaAPIException quando ocorre qualquer problema durante a requisição para a API de Economia
     */
    Float getCurrentUSDQuotationInReais() throws EconomiaAPIException;

}
