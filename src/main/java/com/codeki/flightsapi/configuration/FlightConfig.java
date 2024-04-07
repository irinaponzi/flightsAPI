package com.codeki.flightsapi.configuration;

import com.codeki.flightsapi.model.Dollar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Configuration
public class FlightConfig {

    private static final String URL_ALL_DOLLARS = "https://dolarapi.com/v1/dolares";
    private static final String URL_DOLLAR_CARD = "https://dolarapi.com/v1/dolares/tarjeta";

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public Dollar[] fetchAllDollars() {
        RestTemplate restTemplate = restTemplate();
        return restTemplate.getForEntity(URL_ALL_DOLLARS, Dollar[].class).getBody();
    }

    public Dollar fetchDollarCard() {
        RestTemplate restTemplate = restTemplate();
        try {
            return restTemplate.getForObject(URL_DOLLAR_CARD, Dollar.class);
        } catch (RestClientException e) {
            return new Dollar(); // Ver esto
        }
    }

}
