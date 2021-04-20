package com.defaultvalue.petsclinic.issue;

import com.defaultvalue.petsclinic.issue.dto.IssueDTO;
import com.defaultvalue.petsclinic.issue.entity.Issue;
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

    @Autowired
    public IssueController(IssueService issueService, IssueRepository issueRepository) {
        this.issueService = issueService;
        this.issueRepository = issueRepository;
    }

    @PostMapping
    @Secured("ROLE_USER")
    public void newIssue(IssueDTO issueDTO) {
        issueService.save(issueDTO);
    }

    @GetMapping("/{id}")
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
    public List<Issue> getIssuesByPetID(@PathVariable Long petId) {
        return issueRepository.findAllByPetId(petId);
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
        return "/issue/list-issues";
    }
}
