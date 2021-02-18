package com.idrust.bmfpriceapi.services;

public interface CropService {

    /**
     * Calcula o preço da cultura na data desejada
     *
     * @param cropId    Cultura que se deseja o preço
     * @param date      Data para filtrar o período em que se deseja o preço
     * @return  O preço da cultura na data informada (aqui eu estou supondo que o preço devolvido pela API do quandl já
     *          devolve o preço em USD/Kg e daí dentro do serviço eu apenas calculei a conversão para R$)
     */
    Float calculateCropPrice(String cropId, String date);

}
