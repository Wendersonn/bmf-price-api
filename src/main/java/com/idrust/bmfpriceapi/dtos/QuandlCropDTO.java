package com.idrust.bmfpriceapi.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QuandlCropDTO {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Dataset {
        Object[][] data;

        public void setData(Object[][] data) {
            this.data = data;
        }
    }

    private Dataset dataset;

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    public Double getCropPrice() {
        // TODO: tratar caso o objeto não seja populado
        return (Double) this.dataset.data[0][1];
    }

}
