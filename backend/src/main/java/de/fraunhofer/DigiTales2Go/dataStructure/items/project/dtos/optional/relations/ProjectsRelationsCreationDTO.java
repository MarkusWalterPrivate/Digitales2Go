package de.fraunhofer.DigiTales2Go.dataStructure.items.project.dtos.optional.relations;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Projects relations creation dto.
 *
 * @author Markus Walter
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectsRelationsCreationDTO {
    private List<String> fundingSources = new ArrayList<>();

    private List<String> promoters = new ArrayList<>();

    private List<String> projectPartners = new ArrayList<>();

    private List<String> usePartners = new ArrayList<>();

}
