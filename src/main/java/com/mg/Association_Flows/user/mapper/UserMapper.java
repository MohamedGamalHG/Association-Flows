package com.mg.Association_Flows.user.mapper;

import com.mg.Association_Flows.user.domain.entity.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

//    Source = the object you are mapping from → in my case: UserDto
//    Target = the object you are mapping to → usually my User entity

    @Mapping(source = "profileImage",target = "profilePic")
    User mapToEntity(UserDto userDto);

    @Mapping(target = "passwordHash",ignore = true)
    @Mapping(source = "profilePic",target = "profileImage")
    UserDto mapToDto(User user);

    List<UserDto> mapToDtoList(List<User> users);

    @Mapping(source = "profileImage",target = "profilePic")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserDto dto, @MappingTarget User entity);
}
