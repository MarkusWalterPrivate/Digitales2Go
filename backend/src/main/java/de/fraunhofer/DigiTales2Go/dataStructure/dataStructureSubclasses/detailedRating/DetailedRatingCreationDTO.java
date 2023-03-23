package de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.detailedRating;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * The type Detailed priorRating creation dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailedRatingCreationDTO {


    @NotNull
    @Min(1)
    @Max(5)
    private double degreeOfInnovation;
    @NotNull
    @Min(1)
    @Max(5)
    private double disruptionPotential;
    @NotNull
    @Min(1)
    @Max(5)
    private double useCases;

}
