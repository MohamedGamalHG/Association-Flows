package com.mg.Association_Flows.user.domain.entity;

import com.mg.Association_Flows.user.enums.AccountStatus;
import com.mg.Association_Flows.user.enums.RoleType;
import com.mg.Association_Flows.util.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
// i add the table name because user is reserved key word in postgresql
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    private String fullName;
    private String phoneNumber;
    private String email;
    private String passwordHash;
    private String nationalId;
    private String profilePic;
    private Boolean isVerified;
    //    private String fcm_token ; will wait for this column
    private String preferredLang;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
    private Timestamp lastLogin;
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

}
