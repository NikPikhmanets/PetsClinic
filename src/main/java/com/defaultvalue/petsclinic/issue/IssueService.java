package com.defaultvalue.petsclinic.issue;

import com.defaultvalue.petsclinic.issue.dto.IssueDTO;
import com.defaultvalue.petsclinic.issue.entity.Issue;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IssueService {
    void save(IssueDTO issueDTO);

    Issue findById(Long id);

    List<Issue> findAllByDoctorWithStatus(StatusIssue status);

    Page<Issue> findAllNewIssue(int page);
}
