package com.yaavarea.server.model.mapper;

import com.yaavarea.server.model.User;
import com.yaavarea.server.model.UserPass;
import com.yaavarea.server.model.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
//@Component
public interface MapStructMapper {
    User userDtoToUser(UserDto userDto);

    UserPass userDtoToUserPass(UserDto userDto);
}
