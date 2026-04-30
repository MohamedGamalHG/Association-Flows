package com.mg.Association_Flows.user.domain.dtos;

import com.mg.Association_Flows.user.enums.AccountStatus;
import com.mg.Association_Flows.user.enums.RoleType;
import com.mg.Association_Flows.util.BaseDto;
import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto extends BaseDto {
    private String fullName;
    private String phoneNumber;
    private String email;
    private String passwordHash;
    private String nationalId;
    private String profileImage;
    private Boolean isVerified;
    private String preferredLang;
    private AccountStatus accountStatus;
    private Timestamp lastLogin;
    private RoleType roleType;
}
