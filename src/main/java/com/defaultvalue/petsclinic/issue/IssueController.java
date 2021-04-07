package com.defaultvalue.petsclinic.issue;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/issues")
public class IssueController {

    @PostMapping
    public void newIssue(Issue issue) {
        System.out.println(issue);
    }
}
