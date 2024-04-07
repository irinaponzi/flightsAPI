package com.codeki.flightsapi.utils;

import com.codeki.flightsapi.dto.FlightDto;
import com.codeki.flightsapi.model.Flight;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class FlightUtilsTest {

    @Autowired
    FlightUtils flightUtils;

    static List<Flight> flightList;
    static List<FlightDto> flightDtoList;
    static LocalDateTime departureTime;
    static LocalDateTime arrivingTime;
    static Double dollar;

    @BeforeAll
    static void setUp() {

        flightList = new ArrayList<>();
        flightDtoList = new ArrayList<>();
        departureTime = LocalDateTime.of(2024, 3, 1, 10, 0, 0);
        arrivingTime = LocalDateTime.of(2024, 3, 1, 12, 0, 0);
        dollar = 1200.0;

        flightList.add(new Flight(1L, "EZE", "COR", departureTime, arrivingTime, 10.0, "Semanal"));
        flightList.add(new Flight(2L, "EZE", "USU", departureTime, arrivingTime, 80.0, "Mensual"));
        flightList.add(new Flight(3L, "EZE", "MDQ", departureTime, arrivingTime, 8.0, "Diario"));

        flightDtoList.add(new FlightDto(1L, "EZE", "COR", departureTime, arrivingTime, 10.0 * dollar, "Semanal"));
        flightDtoList.add(new FlightDto(2L, "EZE", "USU", departureTime, arrivingTime, 80.0 * dollar, "Mensual"));
        flightDtoList.add(new FlightDto(3L, "EZE", "MDQ", departureTime, arrivingTime, 8.0 * dollar, "Diario"));
    }

    @Test
    void detectOffersTest() {
        Double offerPrice = 10.0;
        List<Flight> offersExpected = Arrays.asList(flightList.get(0), flightList.get(2));

        List<Flight> offersActual = flightUtils.detectOffers(flightList, offerPrice);

        assertEquals(offersExpected.size(), offersActual.size());
        assertEquals(offersExpected.get(0).getId(), offersActual.get(0).getId());
        assertEquals(offersExpected.get(1).getId(), offersActual.get(1).getId());
    }

    @Test
    void flightMapperTest() {
        Flight flight = flightList.get(0);
        FlightDto flightDtoExpected = flightDtoList.get(0);

        FlightDto flightDtoActual = flightUtils.flightMapper(flight, dollar);

        assertEquals(flightDtoExpected.getConvertedPrice(), flightDtoActual.getConvertedPrice());
        assertEquals(flightDtoExpected.getId(), flightDtoActual.getId());
    }

    @Test
    void flightsListMapperTest() {
        List<FlightDto> flightDtoListExpected = flightDtoList;
        List<FlightDto> flightDtoListActual = flightUtils.flightsListMapper(flightList, dollar);

        assertEquals(flightDtoListExpected.size(), flightDtoListActual.size());
        assertThat(flightDtoListActual)
                .allMatch(f -> f instanceof FlightDto);
    }
}
