package com.defaultvalue.petsclinic.issue;

public enum StatusIssue {
    NEW("NEW"),
    IN_PROGRESS("IN PROGRESS"),
    CLOSED("CLOSED");

    private final String status;

    StatusIssue(String status) {
        this.status = status;
    }

    public String getValue() {
        return status;
    }
}
