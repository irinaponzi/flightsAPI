package com.codeki.flightsapi.service;

import com.codeki.flightsapi.dto.FlightDto;
import com.codeki.flightsapi.dto.ResponseDto;
import com.codeki.flightsapi.exceptions.NotFoundException;
import com.codeki.flightsapi.model.Company;
import com.codeki.flightsapi.model.Flight;
import com.codeki.flightsapi.repository.CompanyRepository;
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
    CompanyRepository companyRepository;
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

    public List<FlightDto> findByCompanyName(String companyName) {
        List<Flight> flightList = flightRepository.findByCompanyNameContainingIgnoreCase(companyName);
        if (!flightList.isEmpty()) {
            return utils.flightsListMapper(flightList);
        }
        throw new NotFoundException("No se encontraron resultados");
    }

    public List<FlightDto> getOffers(Integer offerPrice) {
        List<FlightDto> flightsOffers = utils.detectOffers(flightRepository.findAll(), offerPrice);
        if (!flightsOffers.isEmpty()) {
            return flightsOffers;
        }
        throw new NotFoundException("No se encontraron resultados");
    }

    public Flight create(Long companyId, Flight flight) {
        Optional<Company> companyOptional = companyRepository.findById(companyId);
        if (companyOptional.isPresent()) {
            flight.setCompany(companyOptional.get());
            return flightRepository.save(flight);
        }
        throw new NotFoundException("Error al crear el vuelo, la compañía no fue encontrada");
    }

    public Flight update(Long id, Flight flight) {
        Optional<Flight> flightOptional = flightRepository.findById(id);
        if (flightOptional.isPresent()) {
            flight.setId(id);
            return flightRepository.save(flight);
        }
        throw new NotFoundException("No se puede actualizar, el vuelo no fue encontrado");
    }

    public ResponseDto deleteById(Long id) {
        Optional<Flight> flightOptional = flightRepository.findById(id);
        if (flightOptional.isPresent()) {
            flightRepository.deleteById(id);
            return new ResponseDto("El vuelo " + id + " ha sido eliminado");
        }
        throw new NotFoundException("No se puede eliminar, el vuelo no fue encontrado");
    }
}
