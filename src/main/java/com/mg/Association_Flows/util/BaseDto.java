package com.mg.Association_Flows.util;

import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseDto {
    private UUID id;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
