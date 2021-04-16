package com.defaultvalue.petsclinic.issue;


import com.defaultvalue.petsclinic.issue.visit.Visit;

import javax.persistence.*;
import java.util.List;

@Entity(name = "issues")
@NamedEntityGraph(name = "Issue.visits",
        attributeNodes = @NamedAttributeNode("visits")
)
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private StatusIssue statusIssue;

    @OneToMany(mappedBy = "issue", fetch = FetchType.LAZY)
    private List<Visit> visits;

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

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }
}
