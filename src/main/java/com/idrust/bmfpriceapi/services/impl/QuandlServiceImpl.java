package com.idrust.bmfpriceapi.services.impl;

import com.idrust.bmfpriceapi.dtos.QuandlCropDTO;
import com.idrust.bmfpriceapi.properties.QuandlAPIProperties;
import com.idrust.bmfpriceapi.services.QuandlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QuandlServiceImpl implements QuandlService {

    private final RestTemplate restTemplate;
    private final QuandlAPIProperties quandlAPIProperties;

    @Autowired
    public QuandlServiceImpl(RestTemplate restTemplate, QuandlAPIProperties quandlAPIProperties) {
        this.restTemplate = restTemplate;
        this.quandlAPIProperties = quandlAPIProperties;
    }

    /**
     * Calcula qual o preço em USD da cultura na data informada
     *
     * @param cropCode Código da cultura que será procurada
     * @param date     Data em que se deseja o preço daquela cultura
     * @return O preço em dólares (USD) da cultura com o código informado na data informada
     */
    @Override
    public Double getCropPrice(String cropCode, String date) {

        final QuandlCropDTO quandlCropDTO = restTemplate.getForObject(
                quandlAPIProperties.getUrlFor(cropCode, date),
                QuandlCropDTO.class);
        if (quandlCropDTO == null) {
            // TODO: tratar exceção tentando novamente ou pegando valor do banco
            System.out.println("fuuu");
            return null;
        }

        return quandlCropDTO.getCropPrice();
    }

}
