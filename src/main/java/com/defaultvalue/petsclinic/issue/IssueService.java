package com.defaultvalue.petsclinic.issue;

import com.defaultvalue.petsclinic.issue.dto.IssueDTO;
import com.defaultvalue.petsclinic.issue.entity.Issue;
import org.springframework.data.domain.Page;

public interface IssueService {
    void save(IssueDTO issueDTO);

    Issue findById(Long id);

    Page<Issue> findAllByDoctorWithStatus(int page, StatusIssue status);

    Page<Issue> findAllNewIssue(int page);

    Page<Issue> findAllByPetId(Long petId, int page);

    void assignIssue(Long id);
}
