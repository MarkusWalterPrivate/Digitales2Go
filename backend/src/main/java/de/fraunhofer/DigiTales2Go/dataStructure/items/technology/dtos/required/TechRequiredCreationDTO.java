package de.fraunhofer.DigiTales2Go.dataStructure.items.technology.dtos.required;

import de.fraunhofer.DigiTales2Go.dataStructure.dataStructureSubclasses.enumerations.Readiness;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Tech required creation dto.
 *
 * @author Markus Walter
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TechRequiredCreationDTO {
    @NotBlank
    private String description;
    @NotBlank
    private String useCases;
    @NotBlank
    private String discussion;
    @NotEmpty
    private List<String> projectsIAO = new ArrayList<>();
    @NotNull
    private Readiness readiness;

}
