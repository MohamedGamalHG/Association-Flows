package com.mg.Association_Flows.association.enums;

public enum AssociationStatus {
    PENDING, ACTIVE, COMPLETED, CANCELLED;


    public static boolean isValid(String value) {
        for (AssociationStatus s : AssociationStatus.values()) {
            if (s.name().equalsIgnoreCase(value)) return true;
        }
        return false;
    }
}
