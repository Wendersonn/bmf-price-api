package com.idrust.bmfpriceapi.unit.services;

import com.idrust.bmfpriceapi.dtos.EconomiaDTO;
import com.idrust.bmfpriceapi.exceptions.EconomiaAPIException;
import com.idrust.bmfpriceapi.properties.EconomiaAPIProperties;
import com.idrust.bmfpriceapi.services.EconomiaService;
import com.idrust.bmfpriceapi.services.impl.EconomiaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EconomiaServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private EconomiaAPIProperties economiaAPIProperties;

    private EconomiaService economiaService;

    private static final String URL_TESTE = "URL_TESTE";

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        economiaService = new EconomiaServiceImpl(economiaAPIProperties, restTemplate);
    }

    @Test
    public void shouldRetrieveCorrectValueInReaisFromEconomiaAPI() throws EconomiaAPIException {
        final String economiaAPIUrl =  URL_TESTE;
        final EconomiaDTO economiaDTO = new EconomiaDTO();
        economiaDTO.setBid(5.43f);

        final EconomiaDTO[] economiaDTOs = new EconomiaDTO[]{
                economiaDTO
        };

        when(economiaAPIProperties.getEconomiaAPIUrl()).thenReturn(economiaAPIUrl);
        when(restTemplate.getForObject(economiaAPIUrl, EconomiaDTO[].class)).thenReturn(economiaDTOs);

        final Float currentUSDQuotation = economiaService.getCurrentUSDQuotationInReais();

        assertEquals(economiaDTO.getBid(), currentUSDQuotation);
        verify(economiaAPIProperties, times(1)).getEconomiaAPIUrl();
        verify(restTemplate, times(1)).getForObject(economiaAPIUrl, EconomiaDTO[].class);
    }

    @Test
    public void shouldThrowExceptionIfEconomiaAPICallThrowsException() {
        when(economiaAPIProperties.getEconomiaAPIUrl()).thenReturn(URL_TESTE);
        when(restTemplate.getForObject(anyString(), any())).thenThrow(new RuntimeException("Some exception occurred"));

        final Exception expectedException = assertThrows(EconomiaAPIException.class, () -> {
            economiaService.getCurrentUSDQuotationInReais();
        });

        final String expectedMessage = "Erro ao requisitar API de Economia";
        final String actualMessage = expectedException.getMessage();

        assertEquals(expectedMessage, actualMessage);
        verify(economiaAPIProperties, times(1)).getEconomiaAPIUrl();
        verify(restTemplate, times(1)).getForObject(anyString(), any());
    }

    @Test
    public void shouldThrowExceptionIfEconomiaAPICallReturnsNull() {
        when(economiaAPIProperties.getEconomiaAPIUrl()).thenReturn(URL_TESTE);
        when(restTemplate.getForObject(anyString(), any())).thenReturn(null);

        final Exception expectedException = assertThrows(EconomiaAPIException.class, () -> {
            economiaService.getCurrentUSDQuotationInReais();
        });

        final String expectedMessage = "A resposta devolvida pela API de economia não é válida";
        final String actualMessage = expectedException.getMessage();

        assertEquals(expectedMessage, actualMessage);
        verify(economiaAPIProperties, times(1)).getEconomiaAPIUrl();
        verify(restTemplate, times(1)).getForObject(anyString(), any());
    }

    @Test
    public void shouldThrowExceptionIfEconomiaAPICallReturnsEmptyValue() {
        final EconomiaDTO[] economiaDTOs = new EconomiaDTO[]{};

        when(economiaAPIProperties.getEconomiaAPIUrl()).thenReturn(URL_TESTE);
        when(restTemplate.getForObject(anyString(), any())).thenReturn(economiaDTOs);

        final Exception expectedException = assertThrows(EconomiaAPIException.class, () -> {
            economiaService.getCurrentUSDQuotationInReais();
        });

        final String expectedMessage = "A resposta devolvida pela API de economia não é válida";
        final String actualMessage = expectedException.getMessage();

        assertEquals(expectedMessage, actualMessage);
        verify(economiaAPIProperties, times(1)).getEconomiaAPIUrl();
        verify(restTemplate, times(1)).getForObject(anyString(), any());
    }

}
