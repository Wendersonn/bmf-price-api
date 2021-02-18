package com.idrust.bmfpriceapi.controllers;

import com.idrust.bmfpriceapi.services.CropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "crops")
public class CropController {

    private final CropService cropService;

    @Autowired
    public CropController(CropService cropService) {
        this.cropService = cropService;
    }

    @GetMapping(value = "{cropCode}/price")
    public void calculateCropPrice(@PathVariable String cropCode, @RequestParam String date) {
        System.out.println(cropCode + " " + date);
        this.cropService.calculateCropPrice(cropCode, date);
    }

}
