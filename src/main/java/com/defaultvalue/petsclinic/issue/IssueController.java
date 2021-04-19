package com.defaultvalue.petsclinic.issue;

import com.defaultvalue.petsclinic.issue.dto.IssueDTO;
import com.defaultvalue.petsclinic.issue.entity.Issue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/issues")
public class IssueController {

    private final IssueService issueService;
    private final IssueRepository issueRepository;

    @Autowired
    public IssueController(IssueService issueService, IssueRepository issueRepository) {
        this.issueService = issueService;
        this.issueRepository = issueRepository;
    }

    @PostMapping
    public void newIssue(IssueDTO issueDTO) {
        issueService.save(issueDTO);
    }

    @GetMapping("/{id}")
    public Issue getIssueById(@PathVariable String id) {
        return new Issue();
    }

    @GetMapping("/new")
    @Secured("ROLE_DOCTOR")
    public List<IssueDTO> getAllNewIssues() {
        List<Issue> issues = issueRepository.findAllByStatusIssue(StatusIssue.NEW);

        return new IssueDTO().getListIssueDTO(issues);
    }

    @GetMapping("/assigned-to-me")
    @Secured("ROLE_DOCTOR")
    public List<Issue> getAIssuesByDoctor() {
        return Collections.emptyList();
    }

    @GetMapping("/{petId}")
    @Secured("ROLE_DOCTOR")
    public List<Issue> getIssuesByPetID(@PathVariable String petId) {
        return Collections.emptyList();
    }

    @PutMapping
    public void updateIssue(Issue issue) {
        issueRepository.save(issue);
    }

    @GetMapping
    public List<Issue> getIssues() {
        return issueRepository.findAll();
    }
}
