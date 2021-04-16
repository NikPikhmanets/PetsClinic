package com.defaultvalue.petsclinic.issue.visit;

import com.defaultvalue.petsclinic.issue.entity.Issue;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "visits")
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String commentOfDoctor;
    @CreationTimestamp
    private LocalDateTime dateTime;

    @Column(name = "issue_id")
    private long issueId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentOfDoctor() {
        return commentOfDoctor;
    }

    public void setCommentOfDoctor(String commentOfDoctor) {
        this.commentOfDoctor = commentOfDoctor;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public long getIssueId() {
        return issueId;
    }

    public void setIssueId(long issueId) {
        this.issueId = issueId;
    }
}
