package de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.creation;

import de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.creation.subclasses.required.TeamSizeCreationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * The type Company required creation dto.
 *
 * @author Markus Walter
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRequiredCreationDTO {

    @NotBlank
    private String description;
    @NotBlank
    private String useCases;
    @NotNull
    @Valid
    private TeamSizeCreationDTO teamSize = new TeamSizeCreationDTO();
    @NotBlank
    private String website;
    @NotNull
    @Min(0)
    @Max(2100)
    private int foundationYear;
}
