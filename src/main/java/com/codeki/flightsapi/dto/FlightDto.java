package com.codeki.flightsapi.dto;

import com.codeki.flightsapi.model.Company;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FlightDto {

    private Long id;
    private String origin;
    private String destiny;
    private LocalDateTime departureTime;
    private LocalDateTime arrivingTime;
    private Double convertedPrice;
    private String frequency;
    private Company company;

    public FlightDto(Long id, String origin, String destiny, LocalDateTime departureTime, LocalDateTime arrivingTime, Double convertedPrice, String frequency) {
        this.id = id;
        this.origin = origin;
        this.destiny = destiny;
        this.departureTime = departureTime;
        this.arrivingTime = arrivingTime;
        this.convertedPrice = convertedPrice;
        this.frequency = frequency;
    }
    // Ver si alguno de estos construtores no se está usando
    public FlightDto(String origin, String destiny, LocalDateTime departureTime, LocalDateTime arrivingTime, Double convertedPrice, String frequency) {
        this.origin = origin;
        this.destiny = destiny;
        this.departureTime = departureTime;
        this.arrivingTime = arrivingTime;
        this.convertedPrice = convertedPrice;
        this.frequency = frequency;
    }

    public FlightDto(String origin, String destiny, LocalDateTime departureTime, LocalDateTime arrivingTime, Double convertedPrice, String frequency, Company company) {
        this.origin = origin;
        this.destiny = destiny;
        this.departureTime = departureTime;
        this.arrivingTime = arrivingTime;
        this.convertedPrice = convertedPrice;
        this.frequency = frequency;
        this.company = company;
    }
}
