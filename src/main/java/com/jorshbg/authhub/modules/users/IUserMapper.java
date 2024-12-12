package com.jorshbg.authhub.modules.users;

import com.jorshbg.authhub.app.dtos.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IUserMapper {

    IUserMapper INSTANCE  = Mappers.getMapper(IUserMapper.class);

    UserEntity toEntity(UserDto dto);

}
