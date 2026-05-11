package com.mg.Association_Flows.association.domain.dtos;

import com.mg.Association_Flows.association.enums.AssociationStatus;
import com.mg.Association_Flows.user.domain.dtos.UserDto;
import com.mg.Association_Flows.user.domain.entity.User;
import com.mg.Association_Flows.util.BaseDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssociationDto extends BaseDto {

    private UserDto owner;
    private String title;
    private BigDecimal monthlyAmount; // this what customer pay every month
    private Integer totalShares; // عدد الاسهم في الجمعيه زي مثلا 20 اسم كدا يعني
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer collectionDay; // The day designated for collecting the money
    private Integer payoutDay; // The day designated for receiving the money for customer has Ordered
    private String currency;
    private String description;
    private AssociationStatus status;
    private BigDecimal totalPoolAmount; // Total amount received
    private BigDecimal currentCollectedBalance;

}
