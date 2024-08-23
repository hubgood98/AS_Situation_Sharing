package com.example.as_situation_sharing_web.user;

import lombok.Getter;

@Getter
public enum UserRole{
    ADMIN("ROLE_ADMIN"),
    CUSTOMER("ROLE_CUSTOMER"),
    TECHNICIAN("ROLE_TECHNICIAN");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

}
