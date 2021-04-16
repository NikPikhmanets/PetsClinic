package com.defaultvalue.petsclinic.issue.entity;


import com.defaultvalue.petsclinic.issue.StatusIssue;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "issues")
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @Column(name = "pet_id")
    private long petId;

    @Enumerated(EnumType.STRING)
    private StatusIssue statusIssue = StatusIssue.NEW;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatusIssue getStatusIssue() {
        return statusIssue;
    }

    public void setStatusIssue(StatusIssue statusIssue) {
        this.statusIssue = statusIssue;
    }

    public long getPetId() {
        return petId;
    }

    public void setPetId(long petId) {
        this.petId = petId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Issue issue = (Issue) o;

        if (petId != issue.petId) return false;
        if (!Objects.equals(id, issue.id)) return false;
        if (!description.equals(issue.description)) return false;
        return statusIssue == issue.statusIssue;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + description.hashCode();
        result = 31 * result + (int) (petId ^ (petId >>> 32));
        result = 31 * result + statusIssue.hashCode();
        return result;
    }
}
