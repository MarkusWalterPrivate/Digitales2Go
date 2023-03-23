package de.fraunhofer.DigiTales2Go.dataStructure.items.technology.dtos.required;

import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.subclasses.TechRequired;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TechRequiredReturnDTO {

    private Long id;

    private String description = "";

    private String useCases = "";

    private String discussion = "";

    private List<String> projectsIAO = new ArrayList<>();

    private String readiness = "";


    /**
     * Constrcutor from TechRequired
     *
     * @param required the required
     */
    public TechRequiredReturnDTO(TechRequired required) {
        this.id = required.getId();
        this.description = required.getDescription();
        this.discussion = required.getDiscussion();
        this.projectsIAO = required.getProjectsIAO();
        this.readiness = required.getReadiness().getReadinessString();
        this.useCases = required.getUseCases();

    }
}
