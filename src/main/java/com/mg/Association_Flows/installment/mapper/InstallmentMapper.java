package com.mg.Association_Flows.installment.mapper;

import com.mg.Association_Flows.associationSlot.domain.dtos.AssociationSlotDto;
import com.mg.Association_Flows.associationSlot.domain.entity.AssociationSlot;
import com.mg.Association_Flows.associationSlot.mapper.AssociationSlotMapper;
import com.mg.Association_Flows.installment.domain.dto.InstallmentDto;
import com.mg.Association_Flows.installment.domain.entity.Installment;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING
,uses = {AssociationSlotMapper.class})
public interface InstallmentMapper {

    Installment mapToEntity(InstallmentDto installmentDto);
    InstallmentDto mapToDTO(Installment entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateInstallmentFromDto(InstallmentDto installmentDto,@MappingTarget Installment installment);

}
