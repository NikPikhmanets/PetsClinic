package com.defaultvalue.petsclinic.issue;

import com.defaultvalue.petsclinic.issue.dto.IssueDTO;
import com.defaultvalue.petsclinic.issue.entity.Issue;
import com.defaultvalue.petsclinic.login.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class IssueServiceImpl implements IssueService {

    private static final int SIZE_PAGE = 10;

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

    @Override
    public Page<Issue> findAllNewIssue(int page) {
        Pageable requestedPage = PageRequest.of(page, SIZE_PAGE, Sort.by("id").descending());

        return issueRepository.findAllByStatusIssueAndDoctorIdIsNull(StatusIssue.NEW, requestedPage);
    }

    @Override
    public Page<Issue> findAllByPetId(Long petId, int page) {
        Pageable requestedPage = PageRequest.of(page, SIZE_PAGE, Sort.by("id").descending());

        return issueRepository.findAllByPetId(petId, requestedPage);
    }

    private long getUserDetailsId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return userDetails.getId();
    }
}
