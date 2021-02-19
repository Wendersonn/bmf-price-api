package com.idrust.bmfpriceapi.unit.services;

import com.idrust.bmfpriceapi.entities.CropPrice;
import com.idrust.bmfpriceapi.exceptions.CropPriceCalculationException;
import com.idrust.bmfpriceapi.exceptions.EconomiaAPIException;
import com.idrust.bmfpriceapi.repositories.CropPriceRepository;
import com.idrust.bmfpriceapi.services.CropService;
import com.idrust.bmfpriceapi.services.EconomiaService;
import com.idrust.bmfpriceapi.services.QuandlService;
import com.idrust.bmfpriceapi.services.impl.CropServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CropServiceTest {

    @Mock
    private EconomiaService economiaService;

    @Mock
    private QuandlService quandlService;

    @Mock
    private CropPriceRepository cropPriceRepository;

    private CropService cropService;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        cropService = new CropServiceImpl(economiaService, quandlService, cropPriceRepository);
    }

    @Test
    public void shouldRetrieveCropPriceFromDatabaseWhenItExists() throws EconomiaAPIException, CropPriceCalculationException {
        final CropPrice cropPrice = new CropPrice();
        cropPrice.setCode("SOYBEAN");
        cropPrice.setPrice(50.23);
        cropPrice.setDate("2021-02-18");

        when(cropPriceRepository.findByCodeAndDate(cropPrice.getCode(), cropPrice.getDate())).thenReturn(Optional.of(cropPrice));

        final Double actualCropPrice = cropService.calculateCropPrice(cropPrice.getCode(), cropPrice.getDate());

        assertEquals(cropPrice.getPrice(), actualCropPrice);
        verify(cropPriceRepository, times(1)).findByCodeAndDate(cropPrice.getCode(), cropPrice.getDate());
        verify(quandlService, times(0)).getCropPrice(anyString(), anyString());
        verify(economiaService, times(0)).getCurrentUSDQuotationInReais();
    }

    @Test
    public void shouldCallAPIsWhenCropPriceDoesNotExist() throws EconomiaAPIException, CropPriceCalculationException {
        final CropPrice cropPrice = new CropPrice();
        cropPrice.setCode("SOYBEAN");
        cropPrice.setPrice(50d);
        cropPrice.setDate("2021-02-18");

        when(cropPriceRepository.findByCodeAndDate(anyString(), anyString())).thenReturn(Optional.empty());
        when(quandlService.getCropPrice(cropPrice.getCode(), cropPrice.getDate())).thenReturn(10d);
        when(economiaService.getCurrentUSDQuotationInReais()).thenReturn(5f);

        final Double actualCropPrice = cropService.calculateCropPrice(cropPrice.getCode(), cropPrice.getDate());

        assertEquals(cropPrice.getPrice(), actualCropPrice);
        verify(cropPriceRepository, times(1)).findByCodeAndDate(cropPrice.getCode(), cropPrice.getDate());
        verify(quandlService, times(1)).getCropPrice(cropPrice.getCode(), cropPrice.getDate());
        verify(economiaService, times(1)).getCurrentUSDQuotationInReais();
        verify(cropPriceRepository, times(1)).save(any());
    }

    @Test
    public void shouldThrowExceptionIfCropCodeIsNull() {
        final Exception expectedException = assertThrows(IllegalArgumentException.class, () -> {
            cropService.calculateCropPrice(null, "2021-02-18");
        });

        final String expectedMessage = "O {cropCode} é obrigatório.";
        final String actualMessage = expectedException.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void shouldThrowExceptionIfCropCodeIsEmpty() {
        final Exception expectedException = assertThrows(IllegalArgumentException.class, () -> {
            cropService.calculateCropPrice("           ", "2021-02-18");
        });

        final String expectedMessage = "O {cropCode} é obrigatório.";
        final String actualMessage = expectedException.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void shouldThrowExceptionIfDateIsNull() {
        final Exception expectedException = assertThrows(IllegalArgumentException.class, () -> {
            cropService.calculateCropPrice("SOYBEAN", null);
        });

        final String expectedMessage = "O {date} é obrigatório.";
        final String actualMessage = expectedException.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void shouldThrowExceptionIfDateIsEmpty() {
        final Exception expectedException = assertThrows(IllegalArgumentException.class, () -> {
            cropService.calculateCropPrice("SOYBEAN", "         ");
        });

        final String expectedMessage = "O {date} é obrigatório.";
        final String actualMessage = expectedException.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

}
