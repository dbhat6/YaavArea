package com.yaavarea.server.controller;

import com.yaavarea.server.model.Rate;
import com.yaavarea.server.model.dto.EventDto;
import com.yaavarea.server.model.dto.NearbyDto;
import com.yaavarea.server.model.dto.PublicAuthorityDto;
import com.yaavarea.server.model.dto.ResponseDto;
import com.yaavarea.server.model.mongo.Events;
import com.yaavarea.server.model.mongo.PublicAuthorities;
import com.yaavarea.server.service.EventsService;
import com.yaavarea.server.service.PublicAuthoritiesService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/authorities")
@Slf4j
public class PublicAuthorityController {

    private PublicAuthoritiesService publicAuthoritiesService;

    @Autowired
    public PublicAuthorityController(PublicAuthoritiesService publicAuthoritiesService) {
        this.publicAuthoritiesService = publicAuthoritiesService;
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createPublicAuthority(
            @Valid @RequestBody PublicAuthorityDto publicAuthorityDto) {
        log.trace("Creating a new public authority");
        try {
            PublicAuthorities publicAuthorities = publicAuthoritiesService.createPublicAuthority(publicAuthorityDto);
            ResponseDto responseDto = ResponseDto.builder()
                    .message("Public authority created").data(publicAuthorities).build();
            log.trace("Public authority created");
            return ResponseEntity.ok(responseDto);
        } catch (Exception ex) {
            log.error("Unexpected error occurred: {}", ex.getMessage());
            log.error(ex.getStackTrace().toString());
            ResponseDto response = ResponseDto.builder().message("Unexpected error occurred")
                    .errorMessage(ex.getMessage()).build();
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> fetchPublicAuthority(@PathVariable String id) {
        log.trace("Fetching a public authorities");
        try {
            PublicAuthorities publicAuthorities = publicAuthoritiesService.fetchPublicAuthoritiesById(id);
            log.trace("Public authority fetched");
            ResponseDto response = ResponseDto.builder().message("Public authority fetched")
                    .data(publicAuthorities).build();
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException ex) {
            log.error("Public authority with the id {} doesn't exist", id);
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
        double rating = publicAuthoritiesService.fetchRating(id);
        return ResponseEntity.ok(rating);
    }

    @PostMapping("/near")
    public ResponseEntity<ResponseDto> fetchNearby(@RequestBody NearbyDto nearbyDto) {
        log.trace("Fetching public authorities nearby");
        try {
            List<PublicAuthorities> publicAuthoritiesList = publicAuthoritiesService.fetchPublicAuthoritiesNearby(nearbyDto.getPoint(),
                    nearbyDto.getMinimumDistance(), nearbyDto.getMaximumDistance());
            log.trace("Public authorities fetched");
            ResponseDto response = ResponseDto.builder().message("Public authorities fetched")
                    .data(publicAuthoritiesList).build();
            return ResponseEntity.ok(response);
        } catch (ClassCastException ex) {
            log.error("Could not cast result to public authorities: {}", ex.getMessage());
            ResponseDto response = ResponseDto.builder().message("Could not cast result to public authorities")
                    .errorMessage(ex.getMessage()).build();
            return ResponseEntity.status(500).body(response);
        } catch (NoSuchElementException ex) {
            log.error("Public authorities nearby doesn't exist");
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            log.error("Unexpected error occurred: {}", ex.getMessage());
            ResponseDto response = ResponseDto.builder().message("Unexpected error occurred")
                    .errorMessage(ex.getMessage()).build();
            return ResponseEntity.status(500).body(response);
        }
    }
}
