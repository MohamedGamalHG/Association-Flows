package com.mg.Association_Flows.payment.domain.dto;

import com.mg.Association_Flows.associationSlot.domain.dtos.AssociationSlotDto;
import com.mg.Association_Flows.associationSlot.domain.entity.AssociationSlot;
import com.mg.Association_Flows.payment.enums.PaymentMethod;
import com.mg.Association_Flows.payment.enums.PaymentStatus;
import com.mg.Association_Flows.util.BaseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto extends BaseDto {
    // we link this payment by the association slot because if i enter with more than one association slot
    // like slot number 1 and slot number 11 so if the association is 2000 it should pay 4000 to this slots
    private AssociationSlotDto associationSlot;
    private BigDecimal amount;
    private Integer targetMonth; // الشهر الي بيدفع عشانه
    private Timestamp paymentDate;
    private Timestamp confirmationDate; // this time that manager of association confirm the payment
    private PaymentStatus status;
    private PaymentMethod paymentMethod;
    private String attachmentUrl;
    private String memberNote;
    private String managerNote;
    private Boolean isAdvance; // هل ده دفع مقدم لشهر مثلا قدام او شهرين
}
