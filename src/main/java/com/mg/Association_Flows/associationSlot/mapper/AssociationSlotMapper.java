package com.mg.Association_Flows.associationSlot.mapper;

import com.mg.Association_Flows.association.mapper.AssociationMapper;
import com.mg.Association_Flows.associationSlot.domain.dtos.AssociationSlotDto;
import com.mg.Association_Flows.associationSlot.domain.entity.AssociationSlot;
import com.mg.Association_Flows.user.mapper.UserMapper;
import org.mapstruct.*;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
        ,uses =  {UserMapper.class, AssociationMapper.class}
)
public interface AssociationSlotMapper {

    AssociationSlot mapToEntity(AssociationSlotDto associationSlotDto);
    AssociationSlotDto mapToDTO(AssociationSlot entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAssociationSlotFromDto(AssociationSlotDto associationSlotDto,@MappingTarget AssociationSlot associationSlot);
}
