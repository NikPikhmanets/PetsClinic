package com.defaultvalue.petsclinic.issue;

import com.defaultvalue.petsclinic.issue.dto.IssueDTO;
import com.defaultvalue.petsclinic.issue.entity.Issue;
import com.defaultvalue.petsclinic.issue.visit.Visit;
import com.defaultvalue.petsclinic.issue.visit.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.defaultvalue.petsclinic.issue.IssueController.ROLE_DOCTOR;

@RestController
@RequestMapping("/issues")
@Secured(ROLE_DOCTOR)
public class IssueRestController {

    private final IssueService issueService;
    private final IssueRepository issueRepository;
    private final VisitRepository visitRepository;

    @Autowired
    public IssueRestController(IssueService issueService, IssueRepository issueRepository, VisitRepository visitRepository) {
        this.issueService = issueService;
        this.issueRepository = issueRepository;
        this.visitRepository = visitRepository;
    }

    @PostMapping
    @Secured("ROLE_USER")
    public void addIssue(IssueDTO issueDTO) {
        issueService.save(issueDTO);
    }

    @GetMapping("/info/{id}")
    public Issue getIssueById(@PathVariable Long id) {
        return issueService.findById(id);
    }

    @GetMapping("/status/{status}")
    public List<IssueDTO> getAllIssuesByStatus(@PathVariable StatusIssue status) {
        List<Issue> issues = issueRepository.findAllByStatusIssue(status);

        return new IssueDTO().getListIssueDTO(issues);
    }

    @GetMapping("/assigned/{status}")
    public Page<Issue> getAIssuesForCurrentUser(@PathVariable StatusIssue status,
                                                @RequestParam(name = "page", defaultValue = "0") int page) {
        return issueService.findAllByDoctorWithStatus(page, status);
    }

    @GetMapping("/pets/{petId}")
    public Page<Issue> getIssuesByPetId(@PathVariable Long petId,
                                        @RequestParam(name = "page", defaultValue = "0") int page) {
        return issueService.findAllByPetId(petId, page);
    }

    @GetMapping("/new-list")
    public Page<Issue> getAllNewIssues(@RequestParam(name = "page", defaultValue = "0") int page) {
        return issueService.findAllNewIssue(page);
    }

    @PostMapping("/{id}/visits")
    public Visit addVisitByIssueId(@PathVariable Long id, String comment) {
        Visit visit = new Visit();
        visit.setCommentOfDoctor(comment);
        visit.setIssueId(id);

        return visitRepository.save(visit);
    }

    @PutMapping("/{id}")
    public Issue updateIssue(@PathVariable Long id) {

        return null; // TODO
    }

    @PutMapping
    public void updateIssue(Issue issue) {
        issueRepository.save(issue);
    }
}
