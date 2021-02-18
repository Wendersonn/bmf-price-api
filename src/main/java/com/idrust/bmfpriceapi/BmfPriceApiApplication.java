package com.idrust.bmfpriceapi;

import com.idrust.bmfpriceapi.properties.QuandlAPIProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(QuandlAPIProperties.class)
public class BmfPriceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BmfPriceApiApplication.class, args);
	}

}
