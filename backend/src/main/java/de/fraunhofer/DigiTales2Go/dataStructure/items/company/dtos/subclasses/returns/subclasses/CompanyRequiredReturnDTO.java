package de.fraunhofer.DigiTales2Go.dataStructure.items.company.dtos.subclasses.returns.subclasses;


import de.fraunhofer.DigiTales2Go.dataStructure.items.company.subclasses.CompanyRequired;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * The type Company required return dto.
 */
@Data
public class CompanyRequiredReturnDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
    private String useCases;
    private TeamSizeReturnDTO teamSize;
    private String website;
    private int foundationYear;


    /**
     * Instantiates a new Company required return dto.
     *
     * @param required the required
     */
    public CompanyRequiredReturnDTO(CompanyRequired required) {
        this.id = required.getId();
        this.description = required.getDescription();
        this.useCases = required.getUseCases();
        this.teamSize = new TeamSizeReturnDTO(required.getTeamSize());
        this.website = required.getWebsite();
        this.foundationYear = required.getFoundationYear();
    }

}
