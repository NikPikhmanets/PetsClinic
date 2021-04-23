package com.defaultvalue.petsclinic.issue;

import com.defaultvalue.petsclinic.issue.dto.IssueDTO;
import com.defaultvalue.petsclinic.issue.entity.Issue;
import com.defaultvalue.petsclinic.issue.visit.Visit;
import com.defaultvalue.petsclinic.issue.visit.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

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
    @ResponseStatus(HttpStatus.OK)
    public void addIssue(IssueDTO issueDTO) {
        issueService.save(issueDTO);
    }

    @GetMapping("/info/{id}")
    public Issue getIssueById(@PathVariable Long id) {
        return issueRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Issue with ID:" + id + " not found"));
    }

    @GetMapping("/status/{status}")
    public List<IssueDTO> getAllIssuesByStatus(@PathVariable StatusIssue status) {
        List<Issue> issues = issueRepository.findAllByStatusIssue(status);

        return new IssueDTO().getListIssueDTO(issues);
    }

    @GetMapping("/assigned")
    public Page<Issue> getAIssuesForCurrentUser(@RequestParam(name = "page", defaultValue = "0") int page,
                                                @RequestParam String status) {
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

    @PutMapping("/{id}/assign")
    @ResponseStatus(HttpStatus.OK)
    public void updateIssue(@PathVariable Long id) {
        issueService.assignIssue(id);
    }

    @PutMapping("/{id}/status")
    @ResponseStatus(HttpStatus.OK)
    public void updateIssue(@PathVariable Long id, @RequestBody StatusIssue status) {
        issueService.updateIssueStatus(id, status);
    }
}
