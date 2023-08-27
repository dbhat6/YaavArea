package com.yaavarea.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yaavarea.server.model.Rate;
import com.yaavarea.server.model.dto.LocalBusinessDto;
import com.yaavarea.server.model.dto.NearbyDto;
import com.yaavarea.server.model.dto.ResponseDto;
import com.yaavarea.server.model.mongo.LocalBusiness;
import com.yaavarea.server.service.LocalBusinessService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/localb")
@Slf4j
public class LocalBusinessController {

    private LocalBusinessService localBusinessService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public LocalBusinessController(LocalBusinessService localBusinessService) {
        this.localBusinessService = localBusinessService;
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createLocalBusiness(@Valid @RequestBody LocalBusinessDto localBusinessDto) {
        log.trace("Creating a new local business");
        try {
            LocalBusiness localBusiness = localBusinessService.createLocalBusiness(localBusinessDto);
            ResponseDto responseDto = ResponseDto.builder()
                    .message("Local business created").data(localBusiness).build();
            log.trace("Local business created");
            return ResponseEntity.ok(responseDto);
        } catch (Exception ex) {
            log.error("Unexpected error occurred: {}", ex.getMessage());
            ResponseDto response = ResponseDto.builder().message("Unexpected error occurred")
                    .errorMessage(ex.getMessage()).build();
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ResponseDto> fetchLocalBusinessByName(@PathVariable String name) {
        log.trace("Fetching a local business by name {}", name);
        try {
            LocalBusiness localBusiness = localBusinessService.fetchLocalBusinessByName(name);
            log.trace("Local business fetched");
            ResponseDto response = ResponseDto.builder().message("Local business fetched")
                    .data(localBusiness).build();
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException ex) {
            log.error("Local business with the name {} doesn't exist", name);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            log.error("Unexpected error occurred: {}", ex.getMessage());
            ResponseDto response = ResponseDto.builder().message("Unexpected error occurred")
                    .errorMessage(ex.getMessage()).build();
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<ResponseDto> changeEventExpiry(@PathVariable String type) {
        log.trace("Fetching local businesses by type");
        try {
            List<LocalBusiness> localBusinesses = localBusinessService.fetchLocalBusinessByType(type);
            log.trace("Local businesses fetched");
            ResponseDto response = ResponseDto.builder().message("Local businesses fetched")
                    .data(localBusinesses).build();
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException ex) {
            log.error("Local businesses with the type {} doesn't exist", type);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            log.error("Unexpected error occurred: {}", ex.getMessage());
            ResponseDto response = ResponseDto.builder().message("Unexpected error occurred")
                    .errorMessage(ex.getMessage()).build();
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/near")
    public ResponseEntity<ResponseDto> fetchNearby(@RequestBody NearbyDto nearbyDto) {
        log.trace("Fetching local businesses nearby");
        try {
            List<LocalBusiness> localBusinesses = localBusinessService.fetchLocalBusinessNearby(nearbyDto.getPoint(),
                    nearbyDto.getMinimumDistance(), nearbyDto.getMaximumDistance());
            log.trace("Local businesses fetched");
            ResponseDto response = ResponseDto.builder().message("Local businesses fetched")
                    .data(localBusinesses).build();
            return ResponseEntity.ok(response);
        } catch (ClassCastException ex) {
            log.error("Could not cast result to local businesses: {}", ex.getMessage());
            ResponseDto response = ResponseDto.builder().message("Could not cast result to local businesses")
                    .errorMessage(ex.getMessage()).build();
            return ResponseEntity.status(500).body(response);
        } catch (NoSuchElementException ex) {
            log.error("Local businesses nearby doesn't exist");
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            log.error("Unexpected error occurred: {}", ex.getMessage());
            ResponseDto response = ResponseDto.builder().message("Unexpected error occurred")
                    .errorMessage(ex.getMessage()).build();
            return ResponseEntity.status(500).body(response);
        }
    }

    @PutMapping("/{id}/rate")
    public ResponseEntity<ResponseDto> newRating(@RequestBody Rate rate, @PathVariable String id) {
        try {
            Double newRating = localBusinessService.updateRating(rate, id);
            ResponseDto response = ResponseDto.builder().message("Rating updated")
                    .data(newRating).build();
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException ex) {
            log.error("Event with the id {} doesn't exist", id);
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            log.error("Unexpected error occurred: {}", ex.getMessage());
            ResponseDto response = ResponseDto.builder().message("Unexpected error occurred")
                    .errorMessage(ex.getMessage()).build();
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/{id}/rating")
    public ResponseEntity<Double> fetchEventRating(@PathVariable String id) {
        double rating = localBusinessService.fetchRating(id);
        return ResponseEntity.ok(rating);
    }
}
