package com.yaavarea.server.model.mapper;

import com.yaavarea.server.model.dto.ConstituencyDto;
import com.yaavarea.server.model.mongo.Constituencies;
import com.yaavarea.server.model.mongo.User;
import com.yaavarea.server.model.mongo.UserPass;
import com.yaavarea.server.model.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MapStructMapper {
    User userDtoToUser(UserDto userDto);

    UserPass userDtoToUserPass(UserDto userDto);

    Constituencies constituenciesDtoToConstituencies(ConstituencyDto constituencyDto);
}
