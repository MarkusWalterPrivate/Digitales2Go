package de.fraunhofer.DigiTales2Go.dataStructure.items.project.dtos.optional;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.externalItem.ExternalItem;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.referencedValue.dto.ReferencedValueCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.dtos.optional.relations.ProjectsRelationsCreationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Project optional creation dto.
 *
 * @author Markus Walter
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectOptionalCreationDTO {

    private List<ExternalItem> externalItems = new ArrayList<>();
    private List<String> publications = new ArrayList<>();
    @Valid
    private ProjectsRelationsCreationDTO projectRelations = new ProjectsRelationsCreationDTO();
    @Valid
    private ReferencedValueCreationDTO financing = new ReferencedValueCreationDTO();
    private String website = "";

}
