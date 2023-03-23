package de.fraunhofer.DigiTales2Go.dataStructure.items.project.dtos.optional;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.externalItem.ExternalItem;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.referencedValue.ReferencedValue;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.subclasses.ProjectOptional;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.subclasses.subsubclases.ProjectRelations;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Project optional return dto.
 *
 * @author Markus Walter
 */
@Data
public class ProjectOptionalReturnDTO {
    private Long id;
    private List<ExternalItem> externalItems = new ArrayList<>();
    private List<String> publications = new ArrayList<>();
    private ProjectRelations projectRelations;
    private ReferencedValue financing;
    private String website;


    /**
     * Instantiates a new Project optional return dto.
     *
     * @param optional the optional
     */
    public ProjectOptionalReturnDTO(ProjectOptional optional) {

        this.id = optional.getId();
        this.externalItems = optional.getExternalItems();
        this.publications = optional.getPublications();
        this.projectRelations = optional.getProjectRelations();
        this.financing = optional.getFinancing();
        this.website = optional.getWebsite();
    }
}
