package com.defaultvalue.petsclinic.issue;

import com.defaultvalue.petsclinic.issue.dto.IssueDTO;
import com.defaultvalue.petsclinic.issue.entity.Issue;
import com.defaultvalue.petsclinic.issue.visit.Visit;
import com.defaultvalue.petsclinic.issue.visit.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.defaultvalue.petsclinic.issue.IssueController.ROLE_DOCTOR;

@Controller
@RequestMapping("/issues")
@Secured(ROLE_DOCTOR)
public class IssueController {

    public static final String ROLE_DOCTOR = "ROLE_DOCTOR";

    private final IssueService issueService;
    private final IssueRepository issueRepository;
    private final VisitRepository visitRepository;

    @Autowired
    public IssueController(IssueService issueService, IssueRepository issueRepository, VisitRepository visitRepository) {
        this.issueService = issueService;
        this.issueRepository = issueRepository;
        this.visitRepository = visitRepository;
    }

    @PostMapping
    @Secured("ROLE_USER")
    public void newIssue(IssueDTO issueDTO) {
        issueService.save(issueDTO);
    }

    @GetMapping("/{id}")
    public String getViewIssue(Model model, @PathVariable Long id) {
        model.addAttribute("issueId", id);

        return "issue/issue";
    }

    @GetMapping("/info/{id}")
    @ResponseBody
    public Issue getIssueById(@PathVariable Long id) {
        return issueService.findById(id);
    }

    @GetMapping("/status/{status}")
    public List<IssueDTO> getAllIssuesByStatus(@PathVariable StatusIssue status) {
        List<Issue> issues = issueRepository.findAllByStatusIssue(status);

        return new IssueDTO().getListIssueDTO(issues);
    }

    @GetMapping("/assigned/{status}")
    public List<IssueDTO> getAIssuesByDoctor(@PathVariable StatusIssue status) {
        List<Issue> issues = issueService.findAllByDoctorWithStatus(status);

        return new IssueDTO().getListIssueDTO(issues);
    }

    @GetMapping("/pets/{petId}")
    @ResponseBody
    public Page<Issue> getIssuesByPetID(@PathVariable Long petId, @RequestParam(name = "page", defaultValue = "0") int page) {
        Page<Issue> issuePage = issueService.findAllByPetId(petId, page);
        return issuePage;
    }

    @PutMapping
    public void updateIssue(Issue issue) {
        issueRepository.save(issue);
    }

    @GetMapping("/new-list")
    @ResponseBody
    public Page<Issue> getAllNewIssues(@RequestParam(name = "page", defaultValue = "0") int page) {
        return issueService.findAllNewIssue(page);
    }

    @GetMapping
    public String getView(Model model) {
        model.addAttribute("status", StatusIssue.values());

        return "issue/list-issues";
    }

    @PostMapping("/{id}/visits")
    @ResponseBody
    public Visit addVisitByIssueId(@PathVariable Long id, String comment) {
        Visit visit = new Visit();
        visit.setCommentOfDoctor(comment);
        visit.setIssueId(id);

        return visitRepository.save(visit);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Issue updateIssue(@PathVariable Long id) {

        return null; // TODO
    }
}
