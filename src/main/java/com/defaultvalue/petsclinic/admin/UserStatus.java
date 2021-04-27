package com.defaultvalue.petsclinic.admin;

public enum UserStatus {
    DOCTOR("Doctor"),
    USER("User");

    private final String status;

    UserStatus(String status) {
        this.status = status;
    }

    public String getValue() {
        return status;
    }
}
