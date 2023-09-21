package com.yaavarea.server.service;

import com.yaavarea.server.dao.CommonDaoImpl;
import com.yaavarea.server.model.Rate;
import com.yaavarea.server.model.Rating;
import com.yaavarea.server.model.dto.PublicAuthorityDto;
import com.yaavarea.server.model.mapper.MapStructMapperImpl;
import com.yaavarea.server.model.mongo.PublicAuthorities;
import com.yaavarea.server.repo.PublicAuthoritiesRepo;
import com.yaavarea.server.utils.GeoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPoint;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PublicAuthoritiesService {
    private PublicAuthoritiesRepo publicAuthoritiesRepo;
    private CommonDaoImpl commonDaoImpl;
    private MapStructMapperImpl mapper;

    @Autowired
    public PublicAuthoritiesService(PublicAuthoritiesRepo publicAuthoritiesRepo,
                                    CommonDaoImpl commonDaoImpl,
                                    MapStructMapperImpl mapper) {
        this.publicAuthoritiesRepo = publicAuthoritiesRepo;
        this.commonDaoImpl = commonDaoImpl;
        this.mapper = mapper;
    }

    public PublicAuthorities createPublicAuthority(PublicAuthorityDto publicAuthorityDto) {
        Optional<PublicAuthorities> publicAuthoritiesOptional = publicAuthoritiesRepo
                .findDistinctFirstByName(publicAuthorityDto.getName());
        if (publicAuthoritiesOptional.isPresent()) {
            PublicAuthorities publicAuthorities = publicAuthoritiesOptional.get();
            publicAuthorities.addContact(publicAuthorityDto.getContact());
            GeoJsonMultiPoint geoJsonMultiPoint = GeoUtils.addNewPoint(
                    publicAuthorities.getGeometry(), publicAuthorityDto.getGeometry());
            publicAuthorities.setGeometry(geoJsonMultiPoint);
            return publicAuthoritiesRepo.save(publicAuthorities);
        }
        publicAuthorityDto.setRating(new Rating());
        PublicAuthorities publicAuthorities = mapper.publicAuthoritiesDtoToPublicAuthorities(
                publicAuthorityDto, null);
        publicAuthoritiesRepo.insert(publicAuthorities);
        return publicAuthorities;
    }

    public PublicAuthorities fetchPublicAuthoritiesByName(String name) {
        Optional<PublicAuthorities> publicAuthorities = publicAuthoritiesRepo.findDistinctFirstByName(name);
        if (publicAuthorities.isEmpty())
            throw new NoSuchElementException("Public authority doesn't exist");
        return publicAuthorities.get();
    }

    public PublicAuthorities fetchPublicAuthoritiesById(String id) {
        Optional<PublicAuthorities> publicAuthorities = publicAuthoritiesRepo.findById(id);
        if (publicAuthorities.isEmpty())
            throw new NoSuchElementException("Public authority doesn't exist");
        return publicAuthorities.get();
    }

    public List<PublicAuthorities> fetchPublicAuthoritiesByParty(String party) {
        Pageable page = PageRequest.of(0, 10);
        Page<PublicAuthorities> publicAuthorities = publicAuthoritiesRepo.findAllByPartyIgnoreCase(party, page);
        if (publicAuthorities.isEmpty())
            throw new NoSuchElementException("Public authority doesn't exist");
        return publicAuthorities.getContent();
    }

    public boolean updateRating(Rate rate, String id) {
        Optional<PublicAuthorities> publicAuthoritiesOptional = publicAuthoritiesRepo.findById(id);
        if (publicAuthoritiesOptional.isEmpty())
            throw new NoSuchElementException("No such public authority found");
        PublicAuthorities publicAuthorities = publicAuthoritiesOptional.get();
        boolean newRating = publicAuthorities.getRating().addNewRating(rate.getRating(), rate.getUserId());
        publicAuthoritiesRepo.save(publicAuthorities);
        return newRating;
    }

    public double fetchRating(String id) {
        Optional<PublicAuthorities> publicAuthoritiesOptional = publicAuthoritiesRepo.findById(id);
        if (publicAuthoritiesOptional.isEmpty())
            throw new NoSuchElementException("No such public authority found");
        PublicAuthorities publicAuthorities = publicAuthoritiesOptional.get();
        return publicAuthorities.getRating().fetchRating();
    }

    public List<PublicAuthorities> fetchPublicAuthoritiesNearby(GeoJsonPoint point, double min, double max) {
        List<PublicAuthorities> publicAuthorities = new ArrayList<>();
        List<Object> publicAuthoritiesList = commonDaoImpl
                .findByGeometryNearby(point, min, max, PublicAuthorities.class);
        publicAuthoritiesList.stream().forEach(publicAuthoritiesObject ->
                publicAuthorities.add((PublicAuthorities) publicAuthoritiesObject));
        if (publicAuthorities.isEmpty())
            throw new NoSuchElementException("No nearby public authority found");
        return publicAuthorities;
    }
}
