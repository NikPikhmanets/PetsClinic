package com.defaultvalue.petsclinic.issue;

import com.defaultvalue.petsclinic.issue.dto.IssueDTO;
import com.defaultvalue.petsclinic.issue.entity.Issue;

public interface IssueService {
    void save(IssueDTO issueDTO);

    Issue findById(Long id);
}
