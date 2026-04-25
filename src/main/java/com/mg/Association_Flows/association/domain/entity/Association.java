package com.mg.Association_Flows.association.domain.entity;

import com.mg.Association_Flows.association.enums.AssociationStatus;
import com.mg.Association_Flows.user.domain.entity.User;
import com.mg.Association_Flows.util.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Association extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id",referencedColumnName = "id")
    private User owner;
    private String title;
    private BigDecimal monthlyAmount;
    private Integer totalShares;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer collectionDay;
    private Integer payoutDay;
    private String currency;
    private String description;
    @Enumerated(EnumType.STRING)
    private AssociationStatus status;
    private BigDecimal totalPoolAmount;
}
