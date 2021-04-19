package com.defaultvalue.petsclinic.issue;

import com.defaultvalue.petsclinic.issue.entity.Issue;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

    @EntityGraph(value = "Issue.visits", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Issue> findById(Long id);

    List<Issue> findAllByStatusIssue(StatusIssue statusIssue);
}
