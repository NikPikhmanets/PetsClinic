package com.defaultvalue.petsclinic.issue;

import com.defaultvalue.petsclinic.issue.dto.IssueDTO;
import com.defaultvalue.petsclinic.issue.entity.Issue;
import org.springframework.data.domain.Page;

public interface IssueService {
    void save(IssueDTO issueDTO);

    Page<Issue> findAllByDoctorWithStatus(int page, String status);

    void assignIssue(Long id);

    void updateIssueStatus(Long id, StatusIssue status);
}
