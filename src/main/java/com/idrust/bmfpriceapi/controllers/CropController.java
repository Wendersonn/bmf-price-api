package com.idrust.bmfpriceapi.controllers;

import com.idrust.bmfpriceapi.dtos.BaseResponse;
import com.idrust.bmfpriceapi.dtos.CropPriceDTO;
import com.idrust.bmfpriceapi.exceptions.CropPriceCalculationException;
import com.idrust.bmfpriceapi.exceptions.QuandlAPIException;
import com.idrust.bmfpriceapi.services.CropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "crops")
public class CropController {

    private final CropService cropService;

    @Autowired
    public CropController(CropService cropService) {
        this.cropService = cropService;
    }

    @ResponseBody
    @GetMapping(value = "{cropCode}/price")
    public ResponseEntity<BaseResponse> calculateCropPrice(@PathVariable String cropCode, @RequestParam String date) throws CropPriceCalculationException, QuandlAPIException {
        final CropPriceDTO cropPriceDTO = new CropPriceDTO();
        cropPriceDTO.setPrice(cropService.calculateCropPrice(cropCode, date));
        return ResponseEntity.ok(BaseResponse.ok(cropPriceDTO));
    }

}
