package com.idrust.bmfpriceapi.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "quandl.api")
public class QuandlAPIProperties {

    private static final String CROP_IDENTIFIER = "{CROP}";
    private static final String DATE_IDENTIFIER = "\\{DATE}";

    private final String url;

    /**
     * @param url O template de URL com o campo de CULTURA e DATA
     */
    public QuandlAPIProperties(String url) {
        this.url = url;
    }

    /**
     * Gera a URL, com a cultura e a data informadas, para a API de cotações.
     * No caso, o template para a URL é inserido pelo Spring
     * no construtor da classe e dessa forma podemos simplesmente
     * alterar as variáveis do template para gerar uma URL
     * coesa de acesso à API.
     *
     * @param cropId    A cultura inserida na URL
     * @param date      A data em que se deseja gerar a URL para o preço da cultura
     * @return  A URL gerada para a API de cotação, com âmbos os parâmetros
     */
    public String getUrlFor(String cropId, String date) {
        if (cropId == null || cropId.trim().isEmpty()) {
            throw new IllegalArgumentException("O argumento {cropId} é obrigatório para gerar a URL da API.");
        } else if (date == null || date.trim().isEmpty()) {
            throw new IllegalArgumentException("O argumento {date} é obrigatório para gerar a URL da API.");
        }

        return this.url.replace(CROP_IDENTIFIER, cropId).replaceAll(DATE_IDENTIFIER, date);
    }

}