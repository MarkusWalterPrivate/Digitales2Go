package de.fraunhofer.DigiTales2Go.dataStructure.items.trend.dtos.subclasses;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * The type Trend required creation dto.
 *
 * @author Markus Walter
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrendRequiredCreationDTO {

    @NotBlank
    private String description;
    @NotBlank
    private String discussion;
}
