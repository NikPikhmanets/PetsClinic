package com.defaultvalue.petsclinic.issue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/issues")
public class IssueController {

    private final IssueRepository issueRepository;

    @Autowired
    public IssueController(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    @PostMapping
    public void newIssue(Issue issue) {
        issueRepository.save(issue);
    }

    @GetMapping("/{id}")
    public Issue getIssueById(@PathVariable String id) {
        return new Issue();
    }

    @GetMapping("/new")
    @Secured("ROLE_DOCTOR")
    public List<Issue> getAllNewIssues() {
        return Collections.emptyList();
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
}
