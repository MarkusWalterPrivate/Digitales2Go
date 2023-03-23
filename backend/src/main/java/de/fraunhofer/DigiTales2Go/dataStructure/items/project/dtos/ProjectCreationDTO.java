package de.fraunhofer.DigiTales2Go.dataStructure.items.project.dtos;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.coreFields.dtos.CoreFieldCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.detailedRating.DetailedRatingCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.event.Event;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.dtos.optional.ProjectOptionalCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.project.dtos.required.ProjectRequiredCreationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Project creation dto.
 *
 * @author Markus Walter
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCreationDTO {
    @NotNull
    @Valid
    private CoreFieldCreationDTO coreField;
    @NotNull
    @Valid
    private DetailedRatingCreationDTO detailedRating = new DetailedRatingCreationDTO();

    @NotNull
    @Valid
    private ProjectRequiredCreationDTO projectRequired = new ProjectRequiredCreationDTO();
    @Valid
    private ProjectOptionalCreationDTO projectOptional = new ProjectOptionalCreationDTO();
    private List<Long> internalProjects = new ArrayList<>();
    private List<Long> internalTrends = new ArrayList<>();
    private List<Long> internalTechnologies = new ArrayList<>();
    private List<Long> internalCompanies = new ArrayList<>();
    private List<Event> events = new ArrayList<>();

}
