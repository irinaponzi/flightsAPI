package com.codeki.flightsapi.service;

import com.codeki.flightsapi.dto.FlightDto;
import com.codeki.flightsapi.dto.ResponseDto;
import com.codeki.flightsapi.exceptions.NotFoundException;
import com.codeki.flightsapi.model.Company;
import com.codeki.flightsapi.model.Flight;
import com.codeki.flightsapi.repository.FlightRepository;
import com.codeki.flightsapi.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    @Autowired
    FlightRepository flightRepository;
    @Autowired
    Utils utils;

    public List<FlightDto> findAll() {
        List<Flight> flightsList = flightRepository.findAll();
        return utils.flightsListMapper(flightsList);
    }

    public Flight findById(Long id) {
        Optional<Flight> flightOptional = flightRepository.findById(id);
        if (flightOptional.isPresent()) {
            return flightOptional.get();
        }
        throw new NotFoundException("El vuelo no fue encontrado");
    }

    public List<FlightDto> findByOrigin(String origin) {
        List<Flight> flightsList = flightRepository.findByOriginContainingIgnoreCase(origin);
        if (!flightsList.isEmpty()) {
            return utils.flightsListMapper(flightsList);
        }
        throw new NotFoundException("No se encontraron resultados");
    }

    public List<FlightDto> findByOriginAndDestiny(String origin, String destiny) {
        List<Flight> flightList = flightRepository.findByOriginIgnoreCaseAndDestinyIgnoreCase(origin, destiny);
        if (!flightList.isEmpty()) {
            return utils.flightsListMapper(flightList);
        }
        throw new NotFoundException("No se encontraron resultados");
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
        throw new NotFoundException("El vuelo no fue encontrado");
    }

//    public Flight addCompany(Long idFlight, Company company) {
//        Optional<Flight> flightOptional = flightRepository.findById(idFlight);
//        if (flightOptional.isPresent()) {
//            Flight flight = flightOptional.get();
//            flight.setCompany(company);
//            return flightRepository.save(flight);
//        }
//        throw new NotFoundException("El vuelo no fue encontrado");
//    }

    public ResponseDto deleteById(Long id) {
        Optional<Flight> flightOptional = flightRepository.findById(id);
        if (flightOptional.isPresent()) {
            flightRepository.deleteById(id);
            return new ResponseDto("El vuelo " + id + " ha sido eliminado");
        }
        throw new NotFoundException("El vuelo no fue encontrado");
    }

    public List<FlightDto> getOffers(Integer offerPrice) {
        List<FlightDto> flightsOffers = utils.detectOffers(flightRepository.findAll(), offerPrice);
        if (!flightsOffers.isEmpty()) {
            return flightsOffers;
        }
        throw new NotFoundException("No se encontraron resultados");
    }
}
