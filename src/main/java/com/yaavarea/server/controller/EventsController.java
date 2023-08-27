package com.yaavarea.server.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.yaavarea.server.model.Rate;
import com.yaavarea.server.model.Rating;
import com.yaavarea.server.model.dto.EventDto;
import com.yaavarea.server.model.dto.NearbyDto;
import com.yaavarea.server.model.dto.ResponseDto;
import com.yaavarea.server.model.mongo.Events;
import com.yaavarea.server.model.mongo.LocalBusiness;
import com.yaavarea.server.service.EventsService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/events")
@Slf4j
public class EventsController {

    private EventsService eventsService;

    @Autowired
    public EventsController(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createEvent(@Valid @RequestBody EventDto eventDto) {
        log.trace("Creating a new event");
        try {
            Events events = eventsService.createEvent(eventDto);
            ResponseDto responseDto = ResponseDto.builder()
                    .message("Event created").data(events).build();
            log.trace("Event created");
            return ResponseEntity.ok(responseDto);
        } catch (Exception ex) {
            log.error("Unexpected error occurred: {}", ex.getMessage());
            ResponseDto response = ResponseDto.builder().message("Unexpected error occurred")
                    .errorMessage(ex.getMessage()).build();
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> fetchEvent(@PathVariable String id) {
        log.trace("Fetching a event");
        try {
            Events event = eventsService.fetchEventById(id);
            log.trace("Event fetched");
            ResponseDto response = ResponseDto.builder().message("Event fetched")
                    .data(event).build();
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

    @GetMapping
    public ResponseEntity<ResponseDto> changeEventExpiry(@RequestParam String name, @RequestParam Long daysToAdd) {
        log.trace("Extending event expiry");
        try {
            eventsService.changeEventExpiry(name, daysToAdd);
            log.trace("Event expiry modified");
            ResponseDto response = ResponseDto.builder().message("Event expiry modified").build();
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException ex) {
            log.error("Event with the name {} doesn't exist", name);
            return ResponseEntity.notFound().build();
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
            Double newRating = eventsService.updateRating(rate, id);
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
        double rating = eventsService.fetchRating(id);
        return ResponseEntity.ok(rating);
    }

    @PostMapping("/near")
    public ResponseEntity<ResponseDto> fetchNearby(@RequestBody NearbyDto nearbyDto) {
        log.trace("Fetching events nearby");
        try {
            List<Events> localBusinesses = eventsService.fetchEventsNearby(nearbyDto.getPoint(),
                    nearbyDto.getMinimumDistance(), nearbyDto.getMaximumDistance());
            log.trace("Events fetched");
            ResponseDto response = ResponseDto.builder().message("Local businesses fetched")
                    .data(localBusinesses).build();
            return ResponseEntity.ok(response);
        } catch (ClassCastException ex) {
            log.error("Could not cast result to events: {}", ex.getMessage());
            ResponseDto response = ResponseDto.builder().message("Could not cast result to events")
                    .errorMessage(ex.getMessage()).build();
            return ResponseEntity.status(500).body(response);
        } catch (NoSuchElementException ex) {
            log.error("Events nearby doesn't exist");
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            log.error("Unexpected error occurred: {}", ex.getMessage());
            ResponseDto response = ResponseDto.builder().message("Unexpected error occurred")
                    .errorMessage(ex.getMessage()).build();
            return ResponseEntity.status(500).body(response);
        }
    }
}
