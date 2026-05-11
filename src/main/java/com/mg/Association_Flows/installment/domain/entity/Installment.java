package com.mg.Association_Flows.installment.domain.entity;

import com.mg.Association_Flows.associationSlot.domain.entity.AssociationSlot;
import com.mg.Association_Flows.installment.enums.InstallmentStatus;
import com.mg.Association_Flows.util.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "installments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Installment extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "slot_id", nullable = false)
    private AssociationSlot associationSlot;
    private BigDecimal amount;
    private LocalDate dueDate;
    @Enumerated(EnumType.STRING)
    private InstallmentStatus status; // PENDING, PAID
    private Timestamp paidAt;
}
