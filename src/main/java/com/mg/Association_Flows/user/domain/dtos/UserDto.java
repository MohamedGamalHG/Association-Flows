package com.mg.Association_Flows.user.domain.dtos;

import com.mg.Association_Flows.user.enums.AccountStatus;
import com.mg.Association_Flows.user.enums.RoleType;
import com.mg.Association_Flows.util.BaseDto;
import com.mg.Association_Flows.validation.NotNullBlankValidation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto extends BaseDto {
    @NotNullBlankValidation
    private String fullName;
    @NotNullBlankValidation
    private String phoneNumber;
    private String email;
    private String passwordHash;
    @NotNullBlankValidation
    @Max(value = 14)
    @Min(value = 14)
    private String nationalId;
    private String profileImage;
    private Boolean isVerified;
    private String preferredLang;
    private AccountStatus accountStatus;
    private Timestamp lastLogin;
    private RoleType roleType;
}
