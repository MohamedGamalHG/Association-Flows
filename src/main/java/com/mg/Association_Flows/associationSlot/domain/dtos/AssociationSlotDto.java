package com.mg.Association_Flows.associationSlot.domain.dtos;


import com.mg.Association_Flows.association.domain.dtos.AssociationDto;
import com.mg.Association_Flows.association.domain.entity.Association;
import com.mg.Association_Flows.associationSlot.enums.AssociationSlotStatus;
import com.mg.Association_Flows.user.domain.dtos.UserDto;
import com.mg.Association_Flows.user.domain.entity.User;
import com.mg.Association_Flows.util.BaseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssociationSlotDto extends BaseDto {

    private UserDto user;
    private AssociationDto association;
    private Integer turnOrder;
    private Boolean isPayoutDone;
    private LocalDate payoutDate;
    private Timestamp joinedAt;
    private AssociationSlotStatus status;
    private String aliasName;
    /*
        column as counter to what customer pay until now
        like every month he pay 1K so after 5 month this column will be 5K
        carry the total amount that paid until this month
    */
    private BigDecimal totalPaidSoFar;
    private Integer remainingInstallments;
    private String notes;
    private String guarantorInfo;
    private String createdBy;
    //    this column to say (الدور ده مشغول ولا لسا فاضي حد يخده)
    private Boolean isVacant;
}
