package de.fraunhofer.DigiTales2Go.dataStructure.items.project.dtos.required.runtime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * The type Runtime creation dto.
 *
 * @author Markus Walter
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuntimeCreationDTO {

    @NotNull
    private Long start;
    @NotNull
    private Long finished;
    @NotNull
    private boolean isUseOnlyYear;

}
