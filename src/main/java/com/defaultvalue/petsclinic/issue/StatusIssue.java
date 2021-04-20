package com.defaultvalue.petsclinic.issue;

public enum StatusIssue {
    NEW("new"),
    IN_PROGRESS("In progress"),
    CLOSED("closed");

    private final String status;

    StatusIssue(String status) {
        this.status = status;
    }

    public String getValue() {
        return status;
    }
}
