package com.yaavarea.server.service;

import com.yaavarea.server.dao.CommonDaoImpl;
import com.yaavarea.server.model.Rate;
import com.yaavarea.server.model.Rating;
import com.yaavarea.server.model.dto.EventDto;
import com.yaavarea.server.model.mapper.MapStructMapperImpl;
import com.yaavarea.server.model.mongo.Events;
import com.yaavarea.server.model.mongo.LocalBusiness;
import com.yaavarea.server.repo.EventsRepo;
import com.yaavarea.server.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class EventsService {
    private EventsRepo eventsRepo;
    private CommonDaoImpl commonDaoImpl;
    private MapStructMapperImpl mapper;

    @Autowired
    public EventsService(EventsRepo eventsRepo, CommonDaoImpl commonDaoImpl, MapStructMapperImpl mapper) {
        this.eventsRepo = eventsRepo;
        this.commonDaoImpl = commonDaoImpl;
        this.mapper = mapper;
    }

    public Events createEvent(EventDto eventDto) {
        eventDto.setRating(new Rating());
        Events events = mapper.eventsDtoToEvents(eventDto);
        eventsRepo.insert(events);
        return events;
    }

    public Events fetchEventById(String id) {
        Optional<Events> event = eventsRepo.findById(id);
        if(event.isPresent())
            return event.get();
        else
            throw new NoSuchElementException("No event found!");
    }

    public void changeEventExpiry(String name, Long daysToAdd) {
        Optional<Events> event = eventsRepo.findDistinctFirstByName(name);
        if (event.isEmpty())
            throw new NoSuchElementException("No such event found");
        Events modifiedEvent = event.get();
        modifiedEvent.setExpireAt(DateUtils.addDays(modifiedEvent.getExpireAt(), daysToAdd));
        eventsRepo.save(modifiedEvent);
    }

    public double updateRating(Rate rate, String id) {
        Optional<Events> event = eventsRepo.findById(id);
        if(event.isEmpty())
            throw new NoSuchElementException("No such event found");
        Events updatedEvent = event.get();
        updatedEvent.getRating().addNewRating(rate.getRating(), rate.getName());
        eventsRepo.save(updatedEvent);
        return updatedEvent.getRating().fetchRating();
//        rating.addNewRating(rate.getRating(), rate.getName());
    }

    public double fetchRating(String id) {
        Optional<Events> event = eventsRepo.findById(id);
        if(event.isEmpty())
            throw new NoSuchElementException("No such event found");
        Events fetchedEvent = event.get();
        return fetchedEvent.getRating().fetchRating();
    }

    public List<Events> fetchEventsNearby(GeoJsonPoint point, double min, double max) {
        List<Events> events = new ArrayList<>();
        List<Object> eventsList = commonDaoImpl
                .findByGeometryNearby(point, min, max, Events.class);
        eventsList.stream().forEach(eventsObject ->
                events.add((Events) eventsObject));
        System.out.println(events);
        if(events.isEmpty())
            throw new NoSuchElementException("Local Business doesn't exist");
        return events;
    }
}
