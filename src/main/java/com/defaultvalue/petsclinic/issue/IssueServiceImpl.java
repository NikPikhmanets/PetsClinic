package com.defaultvalue.petsclinic.issue;

import com.defaultvalue.petsclinic.issue.dto.IssueDTO;
import com.defaultvalue.petsclinic.issue.entity.Issue;
import com.defaultvalue.petsclinic.login.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;

    @Autowired
    public IssueServiceImpl(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    @Override
    public void save(IssueDTO issueDTO) {
        Issue issue = new Issue();
        issue.setDescription(issueDTO.getDescription());
        issue.setPetId(issueDTO.getPetId());
        issueRepository.save(issue);
    }

    @Override
    public Issue findById(Long id) {
        return issueRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Issue with ID:" + id + " not found"));
    }

    @Override
    public List<Issue> findAllByDoctorWithStatus(StatusIssue status) {
        return issueRepository.findAllByDoctorIdAndStatusIssue(getUserDetailsId(), status);
    }

    private long getUserDetailsId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return userDetails.getId();
    }
}
