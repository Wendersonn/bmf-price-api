package com.idrust.bmfpriceapi.services;

public interface QuandlService {

    /**
     * Calcula qual o preço em USD da cultura na data informada
     *
     * @param cropCode  Código da cultura que será procurada
     * @param date      Data em que se deseja o preço daquela cultura
     * @return O preço em dólares (USD) da cultura com o código informado na data informada
     */
    Float getCropPrice(String cropCode, String date);

}
