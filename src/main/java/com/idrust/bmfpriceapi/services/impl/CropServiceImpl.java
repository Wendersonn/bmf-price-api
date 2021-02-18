package com.idrust.bmfpriceapi.services.impl;

import com.idrust.bmfpriceapi.entities.CropPrice;
import com.idrust.bmfpriceapi.repositories.CropPriceRepository;
import com.idrust.bmfpriceapi.services.CropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CropServiceImpl implements CropService {

    private final CropPriceRepository cropPriceRepository;

    @Autowired
    public CropServiceImpl(CropPriceRepository cropPriceRepository) {
        this.cropPriceRepository = cropPriceRepository;
    }


    @Override
    public Float calculateCropPrice(String cropCode, String date) {
        final CropPrice cropPrice = new CropPrice();
        cropPrice.setCode(cropCode);
        cropPrice.setDate(date);
        cropPrice.setPrice(500f);
        cropPriceRepository.save(cropPrice);

        final Optional<CropPrice> persisted = cropPriceRepository.findByCodeAndDate(cropCode, date);
        if (persisted.isPresent()) {
            CropPrice fromOptional = persisted.get();
            System.out.println(fromOptional.getCode() + " | " + fromOptional.getDate() + " | " + fromOptional.getPrice());
        }
        return 0.0f;
    }

}
