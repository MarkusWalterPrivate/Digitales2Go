package de.fraunhofer.DigiTales2Go.dataStructure.items.project.dtos.required;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.contact.Contact;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.ReadinessProject;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.subclasses.ProjectRequired;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.subclasses.subsubclases.Runtime;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Project required return dto.
 */
@Data
@NoArgsConstructor
public class ProjectRequiredReturnDTO {
    /**
     * The Id.
     */
    Long id;
    @NotEmpty
    private String description = "";
    @NotEmpty
    private String readiness = ReadinessProject.NOTSET.getReadiness();
    @NotEmpty
    private Runtime runtime = new Runtime();
    @NotEmpty
    private List<Contact> contacts = new ArrayList<>();

    /**
     * Instantiates a new Project required return dto.
     *
     * @param required the required
     */
    public ProjectRequiredReturnDTO(ProjectRequired required) {
        this.id = required.getId();
        this.description = required.getDescription();
        this.readiness = required.getReadiness().getReadiness();
        this.runtime = required.getRuntime();
        this.contacts = required.getContacts();
    }
}
