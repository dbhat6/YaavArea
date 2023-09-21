package com.yaavarea.server.controller;

import com.yaavarea.server.model.Rate;
import com.yaavarea.server.model.dto.ResponseDto;
import com.yaavarea.server.model.enums.XpCategoriesEnum;
import com.yaavarea.server.service.EventsService;
import com.yaavarea.server.service.LocalBusinessService;
import com.yaavarea.server.service.PublicAuthoritiesService;
import com.yaavarea.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("rate")
@Slf4j
public class RatingsController {
    private EventsService eventsService;
    private UserService userService;
    private PublicAuthoritiesService publicAuthoritiesService;
    private LocalBusinessService localBusinessService;

    @Autowired
    public RatingsController(EventsService eventsService, UserService userService, PublicAuthoritiesService publicAuthoritiesService, LocalBusinessService localBusinessService) {
        this.eventsService = eventsService;
        this.userService = userService;
        this.publicAuthoritiesService = publicAuthoritiesService;
        this.localBusinessService = localBusinessService;
    }

    @PutMapping("/event/{id}")
    public ResponseEntity<Object> rateEvent(@RequestBody Rate rate, @PathVariable String id) {
        try {
            boolean newRating = eventsService.updateRating(rate, id);
            if(newRating)
                userService.addCardsRated(rate.getUserId(), XpCategoriesEnum.EVENT_RATING);
            ResponseDto response = ResponseDto.builder().message("Rating updated")
                    .data(newRating).build();
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException ex) {
            log.error(ex.getMessage());
            log.error("{} with the id {}", ex.getMessage(), id);
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            log.error("Unexpected error occurred: {}", ex.getMessage());
            ResponseDto response = ResponseDto.builder().message("Unexpected error occurred")
                    .errorMessage(ex.getMessage()).build();
            return ResponseEntity.status(500).body(response);
        }
    }

    @PutMapping("/lb/{id}")
    public ResponseEntity<Object> rateLocalBusiness(@RequestBody Rate rate, @PathVariable String id, Authentication authentication) {
        try {
            System.out.println(authentication.getName());
            System.out.println(authentication.getPrincipal());
            boolean newRating = localBusinessService.updateRating(rate, id);
            if(newRating)
                userService.addCardsRated(rate.getUserId(), XpCategoriesEnum.LOCAL_BUSINESS_RATING);
            ResponseDto response = ResponseDto.builder().message("Rating updated")
                    .data(newRating).build();
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException ex) {
            log.error(ex.getMessage());
            log.error("{} with the id {}", ex.getMessage(), id);
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            log.error("Unexpected error occurred: {}", ex.getMessage());
            ResponseDto response = ResponseDto.builder().message("Unexpected error occurred")
                    .errorMessage(ex.getMessage()).build();
            return ResponseEntity.status(500).body(response);
        }
    }

    @PutMapping("/pa/{id}")
    public ResponseEntity<Object> ratePublicAuthority(@RequestBody Rate rate, @PathVariable String id) {
        try {
            boolean newRating = publicAuthoritiesService.updateRating(rate, id);
            if(newRating)
                userService.addCardsRated(rate.getUserId(), XpCategoriesEnum.PUBLIC_AUTHORITY_RATING);
            ResponseDto response = ResponseDto.builder().message("Rating updated")
                    .data(newRating).build();
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException ex) {
            log.error(ex.getMessage());
            log.error("{} with the id {}", ex.getMessage(), id);
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            log.error("Unexpected error occurred: {}", ex.getMessage());
            ResponseDto response = ResponseDto.builder().message("Unexpected error occurred")
                    .errorMessage(ex.getMessage()).build();
            return ResponseEntity.status(500).body(response);
        }
    }
}
