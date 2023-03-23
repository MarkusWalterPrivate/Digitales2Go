package de.fraunhofer.DigiTales2Go.dataStructure.items.technology.dtos;


import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.coreFields.dtos.CoreFieldCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.detailedRating.DetailedRatingCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.dtos.optional.TechOptionalCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.technology.dtos.required.TechRequiredCreationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


/**
 * The type Tech creation dto.
 *
 * @author Markus Walter
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TechCreationDTO {

    @NotNull
    @Valid
    private CoreFieldCreationDTO coreField = new CoreFieldCreationDTO();
    @NotNull
    @Valid
    private DetailedRatingCreationDTO detailedRating = new DetailedRatingCreationDTO();
    @NotNull
    @Valid
    private TechRequiredCreationDTO techRequired = new TechRequiredCreationDTO();
    @Valid
    private TechOptionalCreationDTO techOptional = new TechOptionalCreationDTO();

    private List<Long> internalProjects = new ArrayList<>();
    private List<Long> internalTrends = new ArrayList<>();
    private List<Long> internalTechnologies = new ArrayList<>();
    private List<Long> internalCompanies = new ArrayList<>();

}
