package com.codeki.flightsapi.controller;

import com.codeki.flightsapi.dto.FlightDto;
import com.codeki.flightsapi.dto.ResponseDto;
import com.codeki.flightsapi.model.Flight;
import com.codeki.flightsapi.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    FlightService flightService;

    @GetMapping("")
    public ResponseEntity<List<FlightDto>> getAllFlights() {
        return new ResponseEntity<>(flightService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightDto> getFlightById(@PathVariable Long id) {
        return new ResponseEntity<>(flightService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/origin")
    public ResponseEntity<List<FlightDto>> getFlightsByOrigin(@RequestParam String origin) {
        return new ResponseEntity<>(flightService.findByOrigin(origin), HttpStatus.OK);
    }

    @GetMapping("/location")
    public ResponseEntity<List<FlightDto>> getFlightsByLocations(@RequestParam String origin, @RequestParam String destiny) {
        return new ResponseEntity<>(flightService.findByOriginAndDestiny(origin, destiny), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {
        return new ResponseEntity<>(flightService.create(flight), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @RequestBody Flight flight) {
        return new ResponseEntity<>(flightService.update(id, flight), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDto> deleteFlight(@PathVariable Long id) {
        return new ResponseEntity<>(flightService.deleteById(id), HttpStatus.OK);
    }

    @GetMapping("/offers")
    public ResponseEntity<List<FlightDto>> getOffers(@RequestParam Integer offerPrice) {
        return new ResponseEntity<>(flightService.getOffers(offerPrice), HttpStatus.OK);
    }

}
