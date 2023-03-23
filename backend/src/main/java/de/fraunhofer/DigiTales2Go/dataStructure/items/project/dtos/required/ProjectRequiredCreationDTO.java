package de.fraunhofer.DigiTales2Go.dataStructure.items.project.dtos.required;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.contact.Contact;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.ReadinessProject;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.dtos.required.runtime.RuntimeCreationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Project required creation dto.
 *
 * @author Markus Walter
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequiredCreationDTO {

    @NotBlank
    private String description;
    @NotNull
    private ReadinessProject readiness = ReadinessProject.NOTSET;
    @NotNull
    @Valid
    private RuntimeCreationDTO runtime = new RuntimeCreationDTO();
    @NotEmpty
    private List<Contact> contacts = new ArrayList<>();

}
