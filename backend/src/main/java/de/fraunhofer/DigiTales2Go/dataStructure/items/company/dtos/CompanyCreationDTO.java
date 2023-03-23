package de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.coreFields.dtos.CoreFieldCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.detailedRating.DetailedRatingCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.creation.CompanyOptionalCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.creation.CompanyRequiredCreationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Company creation dto.
 *
 * @author Markus Walter
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyCreationDTO {
    @NotNull
    @Valid
    private CoreFieldCreationDTO coreField = new CoreFieldCreationDTO();
    @NotNull
    @Valid
    private DetailedRatingCreationDTO detailedRating = new DetailedRatingCreationDTO();
    @NotNull
    @Valid
    private CompanyRequiredCreationDTO companyRequired = new CompanyRequiredCreationDTO();
    @Valid
    private CompanyOptionalCreationDTO companyOptional;
    private List<Long> internalProjects = new ArrayList<>();
    private List<Long> internalTrends = new ArrayList<>();
    private List<Long> internalTechnologies = new ArrayList<>();
    private List<Long> internalCompanies = new ArrayList<>();


}
