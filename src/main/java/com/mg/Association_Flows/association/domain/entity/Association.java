package com.mg.Association_Flows.association.domain.entity;

import com.mg.Association_Flows.association.enums.AssociationStatus;
import com.mg.Association_Flows.user.domain.entity.User;
import com.mg.Association_Flows.util.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "associations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Association extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id",referencedColumnName = "id")
    private User owner;
    private String title;
    private BigDecimal monthlyAmount; // this what customer pay every month
    private Integer totalShares; // عدد الاسهم في الجمعيه زي مثلا 20 اسم كدا يعني
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer collectionDay; // The day designated for collecting the money
    private Integer payoutDay; // The day designated for receiving the money for customer has Ordered
    private String currency;
    private String description;
    @Enumerated(EnumType.STRING)
    private AssociationStatus status;
    private BigDecimal totalPoolAmount; // Total amount received

    private BigDecimal currentCollectedBalance;
}
