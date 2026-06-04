package com.mg.Association_Flows.user.domain.entity;

import com.mg.Association_Flows.user.enums.AccountStatus;
import com.mg.Association_Flows.user.enums.RoleType;
import com.mg.Association_Flows.util.BaseEntity;
import com.mg.Association_Flows.validation.NotNullBlankValidation;
import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Entity
// i add the table name because user is reserved key word in postgresql
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity implements UserDetails {

    @NotNullBlankValidation
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(RoleType.User.name()),
                new SimpleGrantedAuthority(RoleType.Admin.name()));
    }

    @Override
    public @Nullable String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return phoneNumber;
    }
}
