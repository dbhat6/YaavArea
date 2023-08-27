package com.yaavarea.server.model.mapper;

import com.yaavarea.server.model.Contact;
import com.yaavarea.server.model.dto.*;
import com.yaavarea.server.model.mongo.*;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.mongodb.core.geo.GeoJsonMultiPoint;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class MapStructMapper {
    public abstract User userDtoToUser(UserDto userDto);

    public abstract UserPass userDtoToUserPass(UserDto userDto);

    public abstract Constituencies constituenciesDtoToConstituencies(ConstituencyDto constituencyDto);

    public abstract Events eventsDtoToEvents(EventDto eventDto);

    public PublicAuthorities publicAuthoritiesDtoToPublicAuthorities(
            PublicAuthorityDto publicAuthorityDto, PublicAuthorities publicAuthorities) {
        if(publicAuthorities == null)
            publicAuthorities = new PublicAuthorities();
        publicAuthorities.setId(publicAuthorityDto.getId());
        publicAuthorities.setName(publicAuthorityDto.getName());
        publicAuthorities.setDescription(publicAuthorityDto.getDescription());
        publicAuthorities.setRating(publicAuthorityDto.getRating());
        publicAuthorities.setParty(publicAuthorityDto.getParty());
        publicAuthorities.addContact(publicAuthorityDto.getContact());
        GeoJsonMultiPoint geoJsonMultiPoint = new GeoJsonMultiPoint(publicAuthorityDto.getGeometry());
        publicAuthorities.setGeometry(geoJsonMultiPoint);
        return publicAuthorities;
    }

    public LocalBusiness localBusinessDtoToLocalBusiness(LocalBusinessDto localBusinessDto) {
        LocalBusiness localBusiness = new LocalBusiness();
        localBusiness.setId(localBusinessDto.getId());
        localBusiness.setName(localBusinessDto.getName());
        localBusiness.setDescription(localBusinessDto.getDescription());
        localBusiness.setRating(localBusinessDto.getRating());
        localBusiness.setType(localBusinessDto.getType());
        GeoJsonMultiPoint geoJsonMultiPoint = new GeoJsonMultiPoint(localBusinessDto.getGeometry());
        localBusiness.setGeometry(geoJsonMultiPoint);
        return localBusiness;
    }

}
