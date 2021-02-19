package com.idrust.bmfpriceapi.unit.services;

import com.idrust.bmfpriceapi.exceptions.QuandlAPIException;
import com.idrust.bmfpriceapi.properties.QuandlAPIProperties;
import com.idrust.bmfpriceapi.services.QuandlService;
import com.idrust.bmfpriceapi.services.impl.QuandlServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class QuandlServiceTeste {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private QuandlAPIProperties quandlAPIProperties;

    private QuandlService quandlService;

    private final static String URL_TESTE = "URL_TESTE";

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        quandlService = new QuandlServiceImpl(restTemplate, quandlAPIProperties);
    }



    @Test
    public void shouldThrowExceptionIfQuandlAPICallThrowsException() {
        when(quandlAPIProperties.getUrlFor(anyString(), anyString())).thenReturn(URL_TESTE);
        when(restTemplate.getForObject(anyString(), any())).thenThrow(new RuntimeException("Some exception occurred"));

        final Exception expectedException = assertThrows(QuandlAPIException.class, () -> {
            quandlService.getCropPrice(anyString(), anyString());
        });

        final String expectedMessage = "Erro ao requisitar Quandl API";
        final String actualMessage = expectedException.getMessage();

        assertEquals(expectedMessage, actualMessage);
        verify(quandlAPIProperties, times(1)).getUrlFor(anyString(), anyString());
    }

    @Test
    public void shouldThrowExceptionIfQuandlAPICallReturnsNull() {
        when(quandlAPIProperties.getUrlFor(anyString(), anyString())).thenReturn(URL_TESTE);
        when(restTemplate.getForObject(anyString(), any())).thenReturn(null);

        final Exception expectedException = assertThrows(QuandlAPIException.class, () -> {
            quandlService.getCropPrice(anyString(), anyString());
        });

        final String expectedMessage = "A resposta devolvida pela API Quandl não é valida";
        final String actualMessage = expectedException.getMessage();

        assertEquals(expectedMessage, actualMessage);
        verify(quandlAPIProperties, times(1)).getUrlFor(anyString(), anyString());
        verify(restTemplate, times(1)).getForObject(anyString(), any());
    }
}
