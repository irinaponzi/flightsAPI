package com.codeki.flightsapi.service;

import com.codeki.flightsapi.configuration.FlightConfig;
import com.codeki.flightsapi.dto.FlightDto;
import com.codeki.flightsapi.dto.ResponseDto;
import com.codeki.flightsapi.exceptions.ObjectNotFoundException;
import com.codeki.flightsapi.model.Dollar;
import com.codeki.flightsapi.model.Flight;
import com.codeki.flightsapi.repository.FlightRepository;
import com.codeki.flightsapi.utils.FlightUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    @Autowired
    FlightRepository flightRepository;
    @Autowired
    FlightUtils flightUtils;
    @Autowired
    FlightConfig flightConfiguration;

    public List<FlightDto> findAll() {
        List<Flight> flightsList = flightRepository.findAll();
        return flightUtils.flightsListMapper(flightsList, getDollarCart());
    }

    public FlightDto findById(Long id) {
        Optional<Flight> flightOptional = flightRepository.findById(id);
        if (flightOptional.isPresent()) {
            return flightUtils.flightMapper(flightOptional.get(), getDollarCart());
        }
        throw new ObjectNotFoundException("El vuelo no fue encontrado");
    }

    public List<FlightDto> findByOrigin(String origin) {
        List<Flight> flightsList = flightRepository.findByOriginContainingIgnoreCase(origin);
        if (!flightsList.isEmpty()) {
            return flightUtils.flightsListMapper(flightsList, getDollarCart());
        }
        throw new ObjectNotFoundException("No se encontraron resultados");
    }

    public List<FlightDto> findByOriginAndDestiny(String origin, String destiny) {
        List<Flight> flightList = flightRepository.findByOriginIgnoreCaseAndDestinyIgnoreCase(origin, destiny);
        if (!flightList.isEmpty()) {
            return flightUtils.flightsListMapper(flightList, getDollarCart());
        }
        throw new ObjectNotFoundException("No se encontraron resultados");
    }

    public Flight create(Flight flight) {
        flightRepository.save(flight);
        return flight;
    }

    public Flight update(Long id, Flight flight) {
        Optional<Flight> flightOptional = flightRepository.findById(id);
        if (flightOptional.isPresent()) {
            flight.setId(id);
            flightRepository.save(flight);
            return flight;
        }
        throw new ObjectNotFoundException("El vuelo no fue encontrado");
    }

    public ResponseDto deleteById(Long id) {
        Optional<Flight> flightOptional = flightRepository.findById(id);
        if (flightOptional.isPresent()) {
            flightRepository.deleteById(id);
            return new ResponseDto("El vuelo " + id + " ha sido eliminado");
        }
        throw new ObjectNotFoundException("El vuelo no fue encontrado");
    }

    public List<FlightDto> getOffers(Integer offerPrice) {
        Double offerPriceInDollars = offerPrice / getDollarCart();
        List<Flight> flightsOffers = flightUtils.detectOffers(flightRepository.findAll(), offerPriceInDollars);
        if (!flightsOffers.isEmpty()) {
            return flightUtils.flightsListMapper(flightsOffers, getDollarCart());
        }
        throw new ObjectNotFoundException("No se encontraron resultados");
    }

    private Double getDollarCart() {
        Dollar dollar = flightConfiguration.fetchDollarCard();
        return dollar.getPromedio();
    }

}
