package com.idrust.bmfpriceapi.services;

import com.idrust.bmfpriceapi.exceptions.CropPriceCalculationException;

public interface CropService {

    /**
     * Calcula o preço da cultura na data desejada
     *
     * @param cropCode  Cultura que se deseja o preço
     * @param date      Data para filtrar o período em que se deseja o preço
     * @return  O preço da cultura na data informada (aqui eu estou supondo que o preço devolvido pela API do quandl já
     *          devolve o preço em USD/Kg e daí dentro do serviço eu apenas calculei a conversão para R$)
     * @throws CropPriceCalculationException quando qualquer problema ocorre durante o cálculo do preço da cultura
     */
    Double calculateCropPrice(String cropCode, String date) throws CropPriceCalculationException;

}
