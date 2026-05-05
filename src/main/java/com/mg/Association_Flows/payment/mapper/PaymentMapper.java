package com.mg.Association_Flows.payment.mapper;

import com.mg.Association_Flows.associationSlot.domain.dtos.AssociationSlotDto;
import com.mg.Association_Flows.associationSlot.domain.entity.AssociationSlot;
import com.mg.Association_Flows.associationSlot.mapper.AssociationSlotMapper;
import com.mg.Association_Flows.payment.domain.dto.PaymentDto;
import com.mg.Association_Flows.payment.domain.entity.Payment;
import com.mg.Association_Flows.payment.enums.PaymentStatus;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,uses = {AssociationSlotMapper.class})
public interface PaymentMapper {

    @Mapping(target = "status",defaultValue = "PENDING")
    Payment mapToEntity(PaymentDto paymentDto);
    PaymentDto mapToDTO(Payment entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePaymentFromDto(PaymentDto paymentDto,@MappingTarget Payment payment);

}
