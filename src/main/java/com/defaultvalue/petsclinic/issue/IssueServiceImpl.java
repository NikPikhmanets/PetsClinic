package com.defaultvalue.petsclinic.issue;

import com.defaultvalue.petsclinic.issue.dto.IssueDTO;
import com.defaultvalue.petsclinic.issue.entity.Issue;
import com.defaultvalue.petsclinic.login.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class IssueServiceImpl implements IssueService {

    public static final int SIZE_PAGE = 10;

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
    public Page<Issue> findAllByDoctorWithStatus(int page, String status) {
        PageRequest pageRequest = PageRequest.of(page, SIZE_PAGE, Sort.by("id").descending());

        if (status.equalsIgnoreCase("ALL")) {
            return issueRepository.findAllByDoctorId(getUserDetailsId(), pageRequest);
        }
        return issueRepository.findAllByDoctorIdAndStatusIssue(getUserDetailsId(), StatusIssue.valueOf(status), pageRequest);
    }

    @Override
    public void assignIssue(Long id) {
        Issue issue = issueRepository.findByIdAndDoctorIdIsNull(id)
                .orElseThrow(() -> new NoSuchElementException("Issue with ID:" + id + " not found or Doctor assigned already"));
        issue.setDoctorId(getUserDetailsId());
        issueRepository.save(issue);
    }

    @Override
    public void updateIssueStatus(Long id, StatusIssue status) {
        Issue issue = issueRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Issue with ID:" + id + " not found"));
        issue.setStatusIssue(status);
        issueRepository.save(issue);
    }

    private long getUserDetailsId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return userDetails.getId();
    }
}
