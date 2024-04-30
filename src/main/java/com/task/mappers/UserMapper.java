package com.task.mappers;

import com.task.dtos.request.AddAndPutUserDTO;
import com.task.dtos.request.PatchUserDTO;
import com.task.dtos.response.GetUserDTO;
import com.task.entities.User;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User dtoToEntity(AddAndPutUserDTO addAndPutUserDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(PatchUserDTO patchUserDTO, @MappingTarget User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
    void updateEntityFromDto(AddAndPutUserDTO addAndPutUserDTO, @MappingTarget User user);

    GetUserDTO entityToDTO(User user);
}
