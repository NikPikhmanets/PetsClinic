package com.defaultvalue.petsclinic.issue;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.defaultvalue.petsclinic.issue.IssueController.ROLE_DOCTOR;

@Controller
@RequestMapping("/issues")
@Secured(ROLE_DOCTOR)
public class IssueController {

    public static final String ROLE_DOCTOR = "ROLE_DOCTOR";

    @GetMapping("/{id}")
    public String getViewForIssue(Model model, @PathVariable Long id) {
        model.addAttribute("issueId", id);

        return "issue/issue";
    }

    @GetMapping
    public String getViewForListOfIssue(Model model) {
        model.addAttribute("status", StatusIssue.values());

        return "issue/list-issues";
    }
}
