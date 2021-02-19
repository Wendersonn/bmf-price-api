package com.idrust.bmfpriceapi.services.impl;

import com.idrust.bmfpriceapi.entities.CropPrice;
import com.idrust.bmfpriceapi.exceptions.CropPriceCalculationException;
import com.idrust.bmfpriceapi.exceptions.EconomiaAPIException;
import com.idrust.bmfpriceapi.exceptions.QuandlAPIException;
import com.idrust.bmfpriceapi.repositories.CropPriceRepository;
import com.idrust.bmfpriceapi.services.CropService;
import com.idrust.bmfpriceapi.services.EconomiaService;
import com.idrust.bmfpriceapi.services.QuandlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CropServiceImpl implements CropService {

    private final EconomiaService economiaService;
    private final QuandlService quandlService;
    private final CropPriceRepository cropPriceRepository;

    @Autowired
    public CropServiceImpl(EconomiaService economiaService,
                           QuandlService quandlService,
                           CropPriceRepository cropPriceRepository) {
        this.economiaService = economiaService;
        this.quandlService = quandlService;
        this.cropPriceRepository = cropPriceRepository;
    }

    @Override
    public Double calculateCropPrice(String cropCode, String date) throws CropPriceCalculationException, QuandlAPIException {
        if (cropCode == null || cropCode.trim().isEmpty()) {
            throw new IllegalArgumentException("O {cropCode} é obrigatório.");
        } else if (date == null || date.trim().isEmpty()) {
            throw new IllegalArgumentException("O {date} é obrigatório.");
        }

        // tentando recuperar o preço da cultura persistido previamente
        final Optional<CropPrice> previouslyPersisted = this.cropPriceRepository.findByCodeAndDate(cropCode, date);
        if (previouslyPersisted.isPresent()) {
            return previouslyPersisted.get().getPrice();
        }

        return retrieveCropPriceFromAPI(cropCode, date).getPrice();
    }

    private CropPrice retrieveCropPriceFromAPI(String cropCode, String date) throws CropPriceCalculationException, QuandlAPIException {
        final Double cropPriceInDollars = this.quandlService.getCropPrice(cropCode, date);
        final Float currentDollarQuotation;
        try {
            currentDollarQuotation = this.economiaService.getCurrentUSDQuotationInReais();
        } catch (EconomiaAPIException e) {
            throw new CropPriceCalculationException("Erro ao chamar o serviço de economia", e);
        }

        return persistCropPrice(cropCode, date, cropPriceInDollars, currentDollarQuotation);
    }

    private CropPrice persistCropPrice(String cropCode,
                                       String date,
                                       Double cropPriceInDollars,
                                       Float currentDollarQuotation) {
        final CropPrice cropPrice = new CropPrice();
        cropPrice.setCode(cropCode);
        cropPrice.setDate(date);
        cropPrice.setPrice(cropPriceInDollars * currentDollarQuotation);

        this.cropPriceRepository.save(cropPrice);

        return cropPrice;
    }

}
