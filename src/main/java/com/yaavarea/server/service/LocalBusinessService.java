package com.yaavarea.server.service;

import com.yaavarea.server.dao.CommonDaoImpl;
import com.yaavarea.server.model.Rate;
import com.yaavarea.server.model.Rating;
import com.yaavarea.server.model.dto.LocalBusinessDto;
import com.yaavarea.server.model.mapper.MapStructMapperImpl;
import com.yaavarea.server.model.mongo.Events;
import com.yaavarea.server.model.mongo.LocalBusiness;
import com.yaavarea.server.repo.LocalBusinessRepo;
import com.yaavarea.server.utils.GeoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class LocalBusinessService {
    private LocalBusinessRepo localBusinessRepo;
    private CommonDaoImpl commonDaoImpl;
    private MapStructMapperImpl mapper;

    @Autowired
    public LocalBusinessService(LocalBusinessRepo localBusinessRepo, CommonDaoImpl commonDaoImpl, MapStructMapperImpl mapper) {
        this.localBusinessRepo = localBusinessRepo;
        this.commonDaoImpl = commonDaoImpl;
        this.mapper = mapper;
    }

    public LocalBusiness createLocalBusiness(LocalBusinessDto localBusinessDto) {
        Optional<LocalBusiness> localBusinessOptional = localBusinessRepo
                .findDistinctFirstByName(localBusinessDto.getName());
        if(localBusinessOptional.isPresent()) {
            GeoJsonMultiPoint geoJsonMultiPoint = GeoUtils.addNewPoint(
                    localBusinessOptional.get().getGeometry(), localBusinessDto.getGeometry());
            localBusinessOptional.get().setGeometry(geoJsonMultiPoint);
            return localBusinessRepo.save(localBusinessOptional.get());
        }
        localBusinessDto.setRating(new Rating());
        LocalBusiness localBusiness = mapper.localBusinessDtoToLocalBusiness(localBusinessDto);
        localBusinessRepo.insert(localBusiness);
        return localBusiness;
    }

    public LocalBusiness fetchLocalBusinessByName(String name) {
        Optional<LocalBusiness> localBusiness = localBusinessRepo.findDistinctFirstByName(name);
        if(localBusiness.isEmpty())
            throw new NoSuchElementException("Local Business doesn't exist");
        return localBusiness.get();
    }

    public List<LocalBusiness> fetchLocalBusinessByType(String type) {
        Pageable page = PageRequest.of(0, 10);
        Page<LocalBusiness> localBusiness = localBusinessRepo.findAllByTypeIgnoreCase(type, page);
        if(localBusiness.isEmpty())
            throw new NoSuchElementException("Local Business doesn't exist");
        return localBusiness.getContent();
    }

    public boolean updateRating(Rate rate, String id) {
        Optional<LocalBusiness> optionalLocalBusiness = localBusinessRepo.findById(id);
        if(optionalLocalBusiness.isEmpty())
            throw new NoSuchElementException("No such local businesses found");
        LocalBusiness localBusiness = optionalLocalBusiness.get();
        boolean newRating = localBusiness.getRating().addNewRating(rate.getRating(), rate.getUserId());
        localBusinessRepo.save(localBusiness);
        return newRating;
    }

    public double fetchRating(String id) {
        Optional<LocalBusiness> optionalLocalBusiness = localBusinessRepo.findById(id);
        if(optionalLocalBusiness.isEmpty())
            throw new NoSuchElementException("No such event found");
        LocalBusiness localBusiness = optionalLocalBusiness.get();
        return localBusiness.getRating().fetchRating();
    }

    public List<LocalBusiness> fetchLocalBusinessNearby(GeoJsonPoint point, double min, double max) {
        List<LocalBusiness> localBusiness = new ArrayList<>();
        List<Object> localBusinessObjects =  commonDaoImpl
                .findByGeometryNearby(point, min, max, LocalBusiness.class);
        localBusinessObjects.stream().forEach(localBusinessObject ->
                localBusiness.add((LocalBusiness) localBusinessObject));
        if(localBusiness.isEmpty())
            throw new NoSuchElementException("Local Business doesn't exist");
        return localBusiness;
    }
}
