package com.mg.Association_Flows.association.mapper;


import com.mg.Association_Flows.association.domain.dtos.AssociationDto;
import com.mg.Association_Flows.association.domain.entity.Association;
import com.mg.Association_Flows.user.mapper.UserMapper;
import org.mapstruct.*;

// Generate implementation AND register it as a Spring bean
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
        , uses = {UserMapper.class} // this mean i have reference key in this relation
)
public interface AssociationMapper {

    Association mapToEntity(AssociationDto associationDto);

    AssociationDto mapToDto(Association association);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAssociationFromDto(AssociationDto associationDto, @MappingTarget Association association);
}
