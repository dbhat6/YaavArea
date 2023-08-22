package com.yaavarea.server.controller;

import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import com.yaavarea.server.model.dto.ConstituencyDto;
import com.yaavarea.server.model.dto.ResponseDto;
import com.yaavarea.server.model.mongo.Constituencies;
import com.yaavarea.server.service.ConstituencyService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/constituency")
@Slf4j
public class ConstituencyController {

    private ConstituencyService constituencyService;

    @Autowired
    public ConstituencyController(ConstituencyService constituencyService) {
        this.constituencyService = constituencyService;
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createConstituency(
            @Valid @RequestBody ConstituencyDto constituencyDto) {
        log.trace("Creating a new constituency");
        try {
            constituencyService.createConstituency(constituencyDto);
            ResponseDto response = ResponseDto.builder().message("Constituency Created").build();
            return ResponseEntity.ok(response);
        } catch (DuplicateKeyException ex) {
            log.error("Constituency already exists");
            ResponseDto response = ResponseDto.builder().message("Constituency already exists")
                    .errorMessage(ex.getMessage()).build();
            return ResponseEntity.status(500).body(response);
        } catch (Exception ex) {
            log.error("Unexpected error occurred: {}", ex.getMessage());
            ResponseDto response = ResponseDto.builder().message("Unexpected error occurred")
                    .errorMessage(ex.getMessage()).build();
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/check")
    public ResponseEntity<ResponseDto> findIntersection(
            @Valid @RequestBody ConstituencyDto constituencyDto) {
        log.trace("Checking");
        try {
            constituencyService.checkIntersection(new Point(new Position(77.6078306985616,
                    12.988851210398877)));
            ResponseDto response = ResponseDto.builder().message("Constituency Created").build();
            return ResponseEntity.ok(response);
        } catch (DuplicateKeyException ex) {
            log.error("Constituency already exists");
            ResponseDto response = ResponseDto.builder().message("Constituency already exists")
                    .errorMessage(ex.getMessage()).build();
            return ResponseEntity.status(500).body(response);
        } catch (Exception ex) {
            log.error("Unexpected error occurred: {}", ex.getMessage());
            ResponseDto response = ResponseDto.builder().message("Unexpected error occurred")
                    .errorMessage(ex.getMessage()).build();
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Object> getConstituencyByName(
            @PathVariable String name) {
        log.trace("Fetching a constituency");
        try {
            Constituencies constituency = constituencyService.getConstituencyByName(name);
            return ResponseEntity.ok(constituency);
        } catch (NoSuchElementException ex) {
            log.error("Constituency doesn't exist");
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            log.error("Unexpected error occurred: {}", ex.getMessage());
            ResponseDto response = ResponseDto.builder().message("Unexpected error occurred")
                    .errorMessage(ex.getMessage()).build();
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Object> countConstituencies() {
        log.trace("Fetching total count of constituencies");
        try {
            long constituencies = constituencyService.countConstituencies();
            return ResponseEntity.ok(constituencies);
        } catch (NoSuchElementException ex) {
            log.error("Constituency doesn't exist");
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            log.error("Unexpected error occurred: {}", ex.getMessage());
            ResponseDto response = ResponseDto.builder().message("Unexpected error occurred")
                    .errorMessage(ex.getMessage()).build();
            return ResponseEntity.status(500).body(response);
        }
    }


}
