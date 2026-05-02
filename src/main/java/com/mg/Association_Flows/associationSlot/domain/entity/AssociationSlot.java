package com.mg.Association_Flows.associationSlot.domain.entity;

import com.mg.Association_Flows.association.domain.entity.Association;
import com.mg.Association_Flows.associationSlot.enums.AssociationSlotStatus;
import com.mg.Association_Flows.user.domain.entity.User;
import com.mg.Association_Flows.util.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
/*
 * this table response for carry the relation of many to many that is result from
 * the user table and the association table
 * so for that reason we divide this relation to 2 @ManyToOne relation in this table
 * with make @ManyToMany in the table User or Association because
 * we add some column or logic in this table so we divide for that reason
 * */
public class AssociationSlot extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "association_id")
    private Association association;
    private Integer turnOrder;
    private Boolean isPayoutDone;// ده بيقول ان العميل ده كان عليه الدور وقبض
    private LocalDate payoutDate;// هنا بنحط التاريخ الي العميل ده قبض فيه الجمعيه
    private Timestamp joinedAt;
    @Enumerated(EnumType.STRING)
    private AssociationSlotStatus status;
    private String aliasName;
    /*
        column as counter to what customer pay until now
        like every month he pay 1K so after 5 month this column will be 5K
        carry the total amount that paid until this month
    */
    private BigDecimal totalPaidSoFar;// هنا بنحط المبالغ الي دفعها لحد دلوقتي يعني مثلا جمعيه ب 2000 بعد خمس شهور دفع هيكون 10000 بنزود عليه يعني
    private Integer remainingInstallments;
    @Lob
    private String notes;
    private String guarantorInfo;
    private String createdBy;
    //    this column to say (الدور ده مشغول ولا لسا فاضي حد يخده)
    private Boolean isReserved;
}
