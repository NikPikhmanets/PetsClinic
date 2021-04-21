package com.defaultvalue.petsclinic.issue;

import com.defaultvalue.petsclinic.issue.entity.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

    Optional<Issue> findById(Long id);

    List<Issue> findAllByStatusIssue(StatusIssue statusIssue);

    Page<Issue> findAllByDoctorIdAndStatusIssue(long doctorId, StatusIssue statusIssue, Pageable pageable);

    Page<Issue> findAllByStatusIssueAndDoctorIdIsNull(StatusIssue statusIssue, Pageable pageable);

    Page<Issue> findAllByPetId(Long id, Pageable pageable);
}
