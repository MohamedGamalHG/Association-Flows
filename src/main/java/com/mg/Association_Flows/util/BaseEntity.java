package com.mg.Association_Flows.util;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.util.UUID;

@MappedSuperclass // this to make the class as parent for other entity to inherit from it
@EntityListeners(AuditingEntityListener.class) // Without it, timestamps won’t be filled
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BaseEntity {
    @Id
    @UuidGenerator
    private UUID id;
    @CreatedDate
    private Timestamp created_at  ;
    @LastModifiedDate
    private Timestamp updated_at;
}
