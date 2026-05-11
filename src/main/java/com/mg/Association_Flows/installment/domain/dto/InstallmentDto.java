package com.mg.Association_Flows.installment.domain.dto;

import com.mg.Association_Flows.associationSlot.domain.entity.AssociationSlot;
import com.mg.Association_Flows.installment.enums.InstallmentStatus;
import com.mg.Association_Flows.util.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InstallmentDto extends BaseDto {

    private AssociationSlot associationSlot;
    private BigDecimal amount;
    private LocalDate dueDate;
    private InstallmentStatus status; // PENDING, PAID
    private Timestamp paidAt;
}
