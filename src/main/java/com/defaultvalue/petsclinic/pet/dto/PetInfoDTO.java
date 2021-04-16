package com.defaultvalue.petsclinic.pet.dto;

import com.defaultvalue.petsclinic.issue.entity.Issue;
import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
public abstract class PetInfoDTO {

    public abstract Long getId();

    public abstract String getName();

    public abstract String getKind();

    public abstract List<Issue> getIssues();
}
