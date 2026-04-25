package com.mg.Association_Flows.payment.domain.entity;

import com.mg.Association_Flows.membership.domain.entity.AssociationSlot;
import com.mg.Association_Flows.payment.enums.PaymentMethod;
import com.mg.Association_Flows.payment.enums.PaymentStatus;
import com.mg.Association_Flows.util.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
/*
 *  this table response for all transactions that will be in the system act as AuditLog
 * */
public class Payment extends BaseEntity {

    // we link this payment by the association slot because if i enter with more than one association slot
    // like slot number 1 and slot number 11 so if the association is 2000 it should pay 4000 to this slots
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "association_slot_id",referencedColumnName = "id")
    private AssociationSlot associationSlot;
    private BigDecimal amount;
    private Integer targetMonth;
    private Timestamp paymentDate;
    private Timestamp confirmationDate;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    @Enumerated(EnumType.STRING)
    private PaymentMethod payment_method;
    private String attachmentUrl;
    private String memberNote;
    private String managerNote;
    private Boolean isAdvance;
}
