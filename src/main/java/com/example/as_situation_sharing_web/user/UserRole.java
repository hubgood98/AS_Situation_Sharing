package com.example.as_situation_sharing_web.user;

import lombok.Getter;

@Getter
public enum UserRole{
    ADMIN("ROLE_ADMIN"),USER("ROLE_USER");

    private String value;

    UserRole(String roleUser) {
        this.value = value;
    }

}
