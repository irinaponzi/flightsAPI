package com.codeki.flightsapi.utils;

import com.codeki.flightsapi.dto.FlightDto;
import com.codeki.flightsapi.model.Dollar;
import com.codeki.flightsapi.model.Flight;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Utils {

    private static final String URL_DOLLAR_CARD = "https://dolarapi.com/v1/dolares/tarjeta";
    private final RestTemplate restTemplate = restTemplate();

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // ver esto, debería llevar excepción
    private Dollar fetchDollarCard() {
        return restTemplate.getForObject(URL_DOLLAR_CARD, Dollar.class);
    }

    public Double getDollarCart() {
        Dollar dollar = fetchDollarCard();
        return dollar.getPromedio();
    }

    public FlightDto flightMapper(Flight flight) {
        Double dollar = getDollarCart();
        return new FlightDto(flight.getId(),flight.getOrigin(), flight.getDestiny(), flight.getDepartureTime(),
                flight.getArrivingTime(), flight.getPrice() * dollar, flight.getFrequency(), flight.getCompany());
    }

    public List<FlightDto> flightsListMapper(List<Flight> flightsList) {
        return flightsList.stream()
                .map(this::flightMapper)
                .collect(Collectors.toList());
    }

    public List<FlightDto> detectOffers(List<Flight> flights, Integer offerPrice) {
        List<FlightDto> flightsDtoList = flightsListMapper(flights);
        return flightsDtoList.stream()
                .filter(flightDto -> flightDto.getConvertedPrice() <= offerPrice)
                .sorted(Comparator.comparing(FlightDto::getConvertedPrice))
                .collect(Collectors.toList());
    }
}
