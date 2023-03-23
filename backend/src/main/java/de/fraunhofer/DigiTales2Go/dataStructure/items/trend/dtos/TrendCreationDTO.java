package de.fraunhofer.DigiTales2Go.dataStructure.items.trend.dtos;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.coreFields.dtos.CoreFieldCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.detailedRating.DetailedRatingCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.dtos.subclasses.TrendOptionalCreationDTO;
import de.fraunhofer.DigiTales2Go.dataStructure.items.trend.dtos.subclasses.TrendRequiredCreationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


/**
 * The type Trend creation dto.
 *
 * @author Markus Walter
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrendCreationDTO {
    @NotNull
    @Valid
    private CoreFieldCreationDTO coreField = new CoreFieldCreationDTO();
    @NotNull
    @Valid
    private DetailedRatingCreationDTO detailedRating = new DetailedRatingCreationDTO();
    @NotNull
    @Valid
    private TrendRequiredCreationDTO trendRequired = new TrendRequiredCreationDTO();
    @Valid
    private TrendOptionalCreationDTO trendOptional = new TrendOptionalCreationDTO();
    private List<Long> internalProjects = new ArrayList<>();
    private List<Long> internalTrends = new ArrayList<>();
    private List<Long> internalTechnologies = new ArrayList<>();
    private List<Long> internalCompanies = new ArrayList<>();

}