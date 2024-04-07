package com.codeki.flightsapi.utils;

import com.codeki.flightsapi.dto.FlightDto;
import com.codeki.flightsapi.model.Flight;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FlightUtils {

    public List<Flight> detectOffers(List<Flight> flights, Double offerPrice) {
        return flights.stream()
                .filter(flight -> flight.getPrice() <= offerPrice)
                .collect(Collectors.toList());
    }

    public FlightDto flightMapper(Flight flight, Double dollar) {
        return new FlightDto(flight.getId(),flight.getOrigin(), flight.getDestiny(), flight.getDepartureTime(),
                flight.getArrivingTime(), flight.getPrice() * dollar, flight.getFrequency());
    }

    public List<FlightDto> flightsListMapper(List<Flight> flightsList, Double dollar) {
        return flightsList.stream()
                .map(flight -> flightMapper(flight, dollar))
                .collect(Collectors.toList());
    }
}
