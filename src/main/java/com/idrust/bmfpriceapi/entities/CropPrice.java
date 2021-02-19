package com.idrust.bmfpriceapi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "crops_quotations")
public class CropPrice extends AbstractEntity {

    @Column(name = "code", nullable = false, updatable = false)
    private String code;

    @Column(name = "price", nullable = false, updatable = false)
    private Double price;

    @Column(name = "date", nullable = false, updatable = false)
    private String date;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
