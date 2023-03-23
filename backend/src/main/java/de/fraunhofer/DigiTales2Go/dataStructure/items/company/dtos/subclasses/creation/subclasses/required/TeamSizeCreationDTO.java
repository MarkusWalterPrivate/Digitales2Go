package de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.creation.subclasses.required;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.TeamSizeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * The type Team size creation dto.
 *
 * @author Markus Walter
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamSizeCreationDTO {

    @NotNull
    private TeamSizeEnum teamSize;
    @NotNull
    @Min(0)
    @Max(2100)
    private int year;
    @NotBlank
    private String reference = "";
}
