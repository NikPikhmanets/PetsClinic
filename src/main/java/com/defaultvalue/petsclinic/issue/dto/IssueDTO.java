package com.defaultvalue.petsclinic.issue.dto;

import com.defaultvalue.petsclinic.issue.entity.Issue;

import java.util.List;
import java.util.stream.Collectors;

public class IssueDTO {

    private long issueId;
    private String description;
    private long petId;

    public IssueDTO() {
    }

    private IssueDTO(long issueId, String description, long petId) {
        this.issueId = issueId;
        this.description = description;
        this.petId = petId;
    }

    public long getIssueId() {
        return issueId;
    }

    public void setIssueId(long issueId) {
        this.issueId = issueId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPetId() {
        return petId;
    }

    public void setPetId(long petId) {
        this.petId = petId;
    }

    public List<IssueDTO> getListIssueDTO(List<Issue> issues) {
        return issues.stream()
                .map(issue -> new IssueDTO(issue.getId(), issue.getDescription(), issue.getPetId()))
                .collect(Collectors.toList());
    }
}
